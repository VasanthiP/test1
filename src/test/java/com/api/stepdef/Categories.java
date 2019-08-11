package com.api.stepdef;

import org.testng.Assert;

import com.api.cucumber.TestContext;
import com.api.helpers.Generic;
import com.api.managers.FileReaderManager;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class Categories {

	TestContext testContext;
	Generic gen;
	
	public Categories(TestContext context) {
		testContext = context;
		gen = FileReaderManager.getInstance().getGenericClass();
	}

	@Given("^I perform \"([^\"]*)\" operation for \"([^\"]*)\"$")
	public void i_perform_operation_for(String strOperation, String strURL) throws Throwable {
		gen.ExecuteAPI(strOperation,strURL);
	}
	
	@Then("^I verify Response http response should be \"([^\"]*)\"$")
	public void i_verify_Response_http_response_should_be(String strStatusCode) throws Throwable {
		Assert.assertTrue(gen.verifyStatuscode(strStatusCode));
	}
	
	
	@Then("^I verify Response HEADER \"([^\"]*)\" should be \"([^\"]*)\"$")
	public void i_verify_Response_HEADER_should_be(String strHeaderName, String strHeaderValue) throws Throwable {
		Assert.assertTrue(gen.verifyResponseHeader(strHeaderName, strHeaderValue));
	}
	
	@Then("^I should see the \"([^\"]*)\" field value as \"([^\"]*)\"$")
	public void i_should_see_the_field_value_as(String strKey, String strValue) throws Throwable {
		Assert.assertTrue(gen.verifyResponseKeyValue(strKey, strValue));
	}
	
	@Then("^I should see \"([^\"]*)\" with the key \"([^\"]*)\" with values \"([^\"]*)\"$")
	public void i_should_see_with_the_key_with_values(String strJsonArrayName, String strKey, String strValue) throws Throwable {
	  Assert.assertTrue(gen.verifyJosnArray(strJsonArrayName, strKey, strValue));
	}



}
