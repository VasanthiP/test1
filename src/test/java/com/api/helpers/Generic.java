package com.api.helpers;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.vimalselvam.cucumber.listener.Reporter;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.internal.http.URIBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

 
	public class Generic {
		
		private static RequestSpecification httpRequest;
		private static Response response;
		private static JSONObject json;
		private static Map<String,String> parameter=new HashMap<String,String>();    
		
		/*******************************************************************************************************************************************************************************************************************
	    'Created By			:Vasanthi					
	    'Created On			: 10-August-2019		
	    'Last Updated By	: NA
	    'Last Updated On	: NA
	    'Parameters Used	: NA
	    'Purpose			: To get the current date and time
		************************************************************************************************************************************************************************************************************************/	
		public String getCuttentTimeStamp() throws CustomizedException{
			Date date;
			SimpleDateFormat ft;
			
			try {
				date = new Date();
				ft = new SimpleDateFormat("Eyyyy.MM.dd'_'hh:mm:ssa");	
			}catch(Exception e) {
				throw new CustomizedException(e.getMessage());					
			}
			return ft.format(date).replaceAll(":", "_");
		}
		
		/*******************************************************************************************************************************************************************************************************************
	    'Created By			:Vasanthi					
	    'Created On			: 10-August-2019		
	    'Last Updated By	: NA
	    'Last Updated On	: NA
	    'Parameters Used	: strSchema(Schema of the URL),strURI(Base URL to build),strPath(path of the URL),strQueryParam(Keys and Values to form URL)
	    'Purpose			: To method is to build URL
		************************************************************************************************************************************************************************************************************************/	
		
		public String BuildURL(String strSchema,URI strURI,String strPath,String strQueryParam) throws CustomizedException, URISyntaxException, IOException {
			String[] arrQueryParam = strQueryParam.split(";");
			URIBuilder url = null;
			try { 
			  url = new URIBuilder(strURI, true, EncoderConfig.encoderConfig());
			}catch(Exception e) {
				throw new CustomizedException(e.getMessage());		
			}
			url.setScheme(strSchema);
			url.setPath(strPath);
			if(!strQueryParam.equalsIgnoreCase("null")) {
				for(int i= 0;i<=arrQueryParam.length-1;i++) {
                  parameter.put(arrQueryParam[i].split(",")[0], arrQueryParam[i].split(",")[1]);
				}			
			}
			url.addQueryParams(parameter);
			parameter.clear();
			return url.toString();	
		}
		
		
		/*******************************************************************************************************************************************************************************************************************
	    'Created By			:Vasanthi					
	    'Created On			: 10-August-2019		
	    'Last Updated By	: NA
	    'Last Updated On	: NA
	    'Parameters Used	: strRequestType - operation to be performed on the request
	    'Purpose			: Method to execute API
		************************************************************************************************************************************************************************************************************************/	
		
		public void ExecuteAPI(String strRequestType,String strURL) throws CustomizedException {
			try { 
			    httpRequest = RestAssured.given();
			
				switch(strRequestType){
					case "POST":
						response = httpRequest.post(strURL);	
						break;
					case "GET":
						response = httpRequest.get(strURL);
						break;
					case "PUT":
						response = httpRequest.put(strURL);
						break;
					case "DELETE":
						response = httpRequest.delete(strURL);
						break;		
				}
			}catch(Exception e) {
				throw new CustomizedException(e.getMessage());
				
			}

		}
		/*******************************************************************************************************************************************************************************************************************
	    'Created By			:Vasanthi					
	    'Created On			: 10-August-2019		
	    'Last Updated By	: NA
	    'Last Updated On	: NA
	    'Parameters Used	: strExpectedValue - value to be compared
	    'Purpose			: Method to verify status code
		************************************************************************************************************************************************************************************************************************/	
		
		public boolean verifyStatuscode(String strExpectedValue) throws CustomizedException {
			boolean blnStatus= false;
			
			int strActualValue = response.getStatusCode();
			if(String.valueOf(strActualValue).equalsIgnoreCase(strExpectedValue)) {
				blnStatus = true;				
			}else {
				Reporter.addStepLog("Expected Value: " + strExpectedValue + " Actual Value: " + strActualValue);
			}
			return blnStatus;		
		}
		
		/*******************************************************************************************************************************************************************************************************************
	    'Created By			:Vasanthi					
	    'Created On			: 10-August-2019		
	    'Last Updated By	: NA
	    'Last Updated On	: NA
	    'Parameters Used	: strResponseHeaderName - Header Name of the response to be verified, strExpectedResHeaderValue - Header value to be verified
	    'Purpose			: Method to verify response header
		************************************************************************************************************************************************************************************************************************/	
		
		public boolean verifyResponseHeader(String strResponseHeaderName, String strExpectedResHeaderValue) {
			boolean blnHeaderValue = false;
			
			String strActualHeaderValue = response.getHeader(strResponseHeaderName);
			if(strExpectedResHeaderValue.equalsIgnoreCase(strActualHeaderValue)) {
				blnHeaderValue = true;	
			}else {
				Reporter.addStepLog("Expected Value: " + strExpectedResHeaderValue + " is not matching the Actual Value: " + strActualHeaderValue);	
			}
			return blnHeaderValue;
			
		}
		
		/*******************************************************************************************************************************************************************************************************************
	    'Created By			:Vasanthi					
	    'Created On			: 10-August-2019		
	    'Last Updated By	: NA
	    'Last Updated On	: NA
	    'Parameters Used	: NA
	    'Purpose			: Method to verify the response body for particular key
		************************************************************************************************************************************************************************************************************************/
		
		public boolean verifyResponseKeyValue(String strKey, String strExpectedvalue) throws CustomizedException {
			boolean blnKeyValue = false;
			String strActualValue = "";
			
			//Create Json Object
			 createJSONObject();
	
			//Get the type of the value and get the value
			Object Obj = json.get(strKey);
			if(Obj instanceof String) {
			    strActualValue = json.getString(strKey);	
			}else if(Obj instanceof Boolean){
				strActualValue = String.valueOf(json.getBoolean(strKey));		
			}
			
			//Compare the actual with expected value
			if(strExpectedvalue.equalsIgnoreCase(strActualValue)) {	
				blnKeyValue= true;
			}else {
			  Reporter.addStepLog("Expected Value: " + strExpectedvalue + " is not matching the Actual Value: " + strActualValue);
			}
			return blnKeyValue;
		}
		
		/*******************************************************************************************************************************************************************************************************************
	    'Created By			:Vasanthi					
	    'Created On			: 10-August-2019		
	    'Last Updated By	: NA
	    'Last Updated On	: NA
	    'Parameters Used	: strJsonArrayName - Name of the JsonArray,strKey - key to be verified in the json array with comma seperated (Ex:Name,Description,price),
	                          strExpectedValueValue - value to be verified the key in the json array with comma seperated(Ex:Basic,Lowest position in category, 0.0000)
	    'Purpose			: Method to verify the response body for particular key
		************************************************************************************************************************************************************************************************************************/
		
		public boolean verifyJosnArray(String strJsonArrayName,String strKey,String strExpectedValueValue) throws CustomizedException {
			boolean blnJsonArrar = false;
			String strActualValue = "";
			int incrementValue = 0;
			
			String[] arrKey = strKey.split(",");
			String[] arrExpectedValue = strExpectedValueValue.split(",");
			
			//Create Json object 
			createJSONObject();
			
			//Loop the JSON object in the selected array
			JSONArray  jsnarray  = json.getJSONArray(strJsonArrayName);
			int length = jsnarray.length();
			for(int i=0;i<=length-1;i++) {
				JSONObject jsonObject  = jsnarray.getJSONObject(i);
				
				//Get the actual value for expected keys
				for(int j=0;j<=arrKey.length-1;j++) {
					strActualValue = strActualValue + "," + jsonObject.getString(arrKey[j].trim());		
				}
				
				//Compare the actual with expected value
				for(int k =0;k<=arrExpectedValue.length-1;k++) {
					if(strActualValue.contains(arrExpectedValue[k])) {
						blnJsonArrar= true;
						incrementValue = incrementValue+1;
					}
				}
				if(blnJsonArrar && (incrementValue == arrExpectedValue.length-1)) {
					blnJsonArrar= true;
					break;
				}	
				strActualValue ="";
			}
			if(blnJsonArrar == false) {
				Reporter.addStepLog("Value Matching " +  strExpectedValueValue + " with the key " + strKey + " is not present in the " + strJsonArrayName + " object");
			}
			return blnJsonArrar;
					
		}
		
		/*******************************************************************************************************************************************************************************************************************
	    'Created By			:Vasanthi					
	    'Created On			: 10-August-2019		
	    'Last Updated By	: NA
	    'Last Updated On	: NA
	    'Parameters Used	: No
	    'Purpose			: Method to create a json object
		************************************************************************************************************************************************************************************************************************/	
		
		public void createJSONObject() throws CustomizedException {
            if(json == null) {
            	try {
            	 json = new JSONObject(response.getBody().asString());
            	}catch(JSONException e) {
            		throw new CustomizedException(e.getMessage());	
            	}
			}
			
		}
		
	}
		
		