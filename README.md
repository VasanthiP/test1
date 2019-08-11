
Overview

Rest Assured is a REST API Java test automation framework.
The goal of the framework is to speed development of tests and validation of APIs by 
abstracting repetitive code and standardizing calls for easier functional and end-to-end testing
across micro-services
Tools Used

1.Maven

2.Cucumber

3.TestNG 

4.Eclipse

5.Rest Assured
Settings

Depending on your infrastructure you may need to add settings.xml file to your local .m2 directory in order for you to be     able to download maven dependencies and build your extension.
Environment Setup

1. Install Java on your computer

  Download and install the Java Software Development Kit (JDK) according to your os of the system
  
2.Install Eclipse IDE(Latest Version)

3.Install TestNG and CucumberEditor for Eclipse from Help -> Eclipse MarketPlace

4. Install Maven for eclipse from Help -> Install New Software

   Name : M2Eclipse
   Location : http://download.eclipse.org/technology/m2e/releases
   
   Note:INLatest Eclipse version , Maven comes with inbuild
   
5.Clone the project from GitHub and Import the excisting maven Project to eclipse 

   File -> Import ->Choose "Existing Maven Project",Select the Root Directory of the project ,
   click on Finish button
Repositories

  <!-- https://mvnrepository.com/artifact/org.testng/testng -->
  <dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.0.0-beta7</version>
    <scope>test</scope>
  </dependency>

  <!-- https://mvnrepository.com/artifact/info.cukes/cucumber-core -->
  <dependency>
    <groupId>info.cukes</groupId>
    <artifactId>cucumber-core</artifactId>
    <version>1.2.5</version>
  </dependency>

  <!-- https://mvnrepository.com/artifact/info.cukes/cucumber-java -->
  <dependency>
    <groupId>info.cukes</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>1.2.5</version>
  </dependency>

  <!-- https://mvnrepository.com/artifact/info.cukes/cucumber-java -->
  <dependency>
    <groupId>info.cukes</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>1.2.5</version>
  </dependency>

  <!-- https://mvnrepository.com/artifact/info.cukes/cucumber-jvm -->
  <dependency>
    <groupId>info.cukes</groupId>
    <artifactId>cucumber-jvm</artifactId>
    <version>1.2.5</version>
    <type>pom</type>
  </dependency>

  <dependency>
    <groupId>info.cukes</groupId>
    <artifactId>cucumber-jvm-deps</artifactId>
    <version>1.0.5</version>
  </dependency>

  <!-- https://mvnrepository.com/artifact/info.cukes/cucumber-testng -->
  <dependency>
    <groupId>info.cukes</groupId>
    <artifactId>cucumber-testng</artifactId>
    <version>1.2.5</version>
  </dependency>

  <!-- https://mvnrepository.com/artifact/info.cukes/gherkin -->
  <dependency>
    <groupId>info.cukes</groupId>
    <artifactId>gherkin</artifactId>
    <version>2.12.2</version>
    <scope>provided</scope>
  </dependency>


  <!-- Reports -->
  <dependency>
    <groupId>com.aventstack</groupId>
    <artifactId>extentreports</artifactId>
    <version>3.1.1</version>
  </dependency>

  <dependency>
    <groupId>com.vimalselvam</groupId>
    <artifactId>cucumber-extentsreport</artifactId>
    <version>3.1.1</version>
  </dependency>


  <dependency>
    <groupId>info.cukes</groupId>
    <artifactId>cucumber-picocontainer</artifactId>
    <version>1.2.5</version>
  </dependency>

  <!-- https://mvnrepository.com/artifact/io.rest-assured/rest-assured -->
  <dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>3.0.0</version>
    <scope>test</scope>
  </dependency>

  <!-- https://mvnrepository.com/artifact/org.json/json -->
  <dependency>
    <groupId>org.json</groupId>
    <artifactId>json</artifactId>
    <version>20180813</version>
  </dependency>

</dependencies>
Design

Feature File

  Path: /Users/vasanthi/Documents/APIAutomation/Feature/Categories.feature

  A Feature File is an entry point to the Cucumber tests.
  This is a file where you will describe your tests in Descriptive language (Like English). 
  It is an essential part of Cucumber, as it serves as an automation test scriptas well as live documents.
  A feature file can contain a scenario or can contain many scenarios in a single feature file 
  but it usually contains a list of scenarios. Let’s create one such file.
  Tag can be given at testcase or feature level in feature file(Ex:Smoke are Regression)
  
Test Runner Class

  Path : /Users/vasanthi/Documents/APIAutomation/src/test/java/com/api/testrunner.java

  Run the cucumber Test
     Right Click on testRunner class and Click Run As  > TestNG 
     
 testrunner class will look like this
     
 @CucumberOptions(
	     format = {"com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html" }, 
    features = { "Feature" },
    glue = { "com/api/stepdef" }, 
    tags = { "@Smoke" }
 )
 
StepDefinition 

 path:/Users/vasanthi/Documents/APIAutomation/src/test/java/com/api/stepdef
 
 All the step definiton should be grouped according to module.
 
 Categories class will look like this:
 
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

Config Properties

  path:/Users/vasanthi/Documents/APIAutomation/src/test/java/com/api/resources/Config.properties
  
  We keep service endpoint URL,Report path,Authentication details and database details in property file,
  so in case you want to store the result in another path, just change the reportpath in property file and that’s it. 
  You do not require to build the whole project  again.
  
  config.properties file will look like this:
 
  reportPath = /target/cucumber-reports/
  reportConfigPath =/src/test/java/com/api/reports/extent-config.xml


Config File Reader

 path: /Users/vasanthi/Documents/APIAutomation/src/test/java/com/api/dataproviders/ConfigFileReader.java
 
  This class is used to read the properies in config properties and ConfigFileReader Method is written 
  according to the business steps.
 

File Reader Manager as Singleton Design Pattern

 path :/Users/vasanthi/Documents/APIAutomation/src/test/java/com/api/managers/FileReaderManager.java 

 Singleton Design Pattern is used in FileReaderManger Class to control object creation, 
 limiting the number of objects to only one. Since there is only one Singleton instance, 
 any instance fields of a Singleton will occur only once per class,just like static fields.
 
Generic Class
 
 path : /Users/vasanthi/Documents/APIAutomation/src/test/java/com/api/helpers/Generic.java
 
 Genric class contains all reusable methods which we can use it across any project

Test Context class 

  Path: /Users/vasanthi/Documents/APIAutomation/src/test/java/com/api/cucumber/TestContext.java
   
  Test Context class which will hold all the objects state.
  we kept the initialization in the constructor and created getMethods() for the objects.
  
    public ScenarioContext scenarioContext;

    public TestContext(){
      scenarioContext =  new ScenarioContext();
    }

    public ScenarioContext getScenarioContext() {
      return scenarioContext;
    }

  Write Constructor for Step Definition classes to share TestContext.Step definition class looks like below.
  
    TestContext testContext;
    Generic gen;

    public Categories(TestContext context) {
      testContext = context;
      gen = FileReaderManager.getInstance().getGenericClass();
    }
  
Scenario Context and Enum Class

  Enum Class Path : /Users/vasanthi/Documents/APIAutomation/src/test/java/com/api/enums/Context.java
  
  suppose if we want to validate the Name of the promotion is listed in some other webservices,
  In that case we need to store the name of the promotion in Scenario Context object while 
  retrieving the promation name from the ScenarioContext and validate the promotion name on the actual webservices.
  
  Enum Class look like below
  
   public enum Context {
      NAME,
      DESCRIPTION;
   }

  path of scenario Context class:
   class:/Users/vasanthi/Documents/APIAutomation/src/test/java/com/api/cucumber/ScenarioContext.java

  Scenario Context is used to Share data between steps in Cucumber using Scenario Context.
  With in this ScenarioContext class, you can create any number of fields to store any form of data.
  It stores the information in the key value pair and again, value can be of any type. 
  It can store String, Boolean, Integer or may be a Class. Also the important point here is that the 
  information which we store in Scenario Context is generated run time. Means during the run if you wish to store 
  some information, you will use Scenario Context. Structure of the TestContext class will be like this:
  
    public void setContext(Context key, Object value) {
        scenarioContext.put(key.toString(), value);
    }

    public Object getContext(Context key){
        return scenarioContext.get(key.toString());
    }

    public Boolean isContains(Context key){
        return scenarioContext.containsKey(key.toString());
    }
    
Cucumber Extent Report

    path: /Users/vasanthi/Documents/APIAutomation/src/test/java/com/api/reports/extent-config.xml
    
    1.Extent Config is required by the Cucumber Extent Report plugin to read the report configuration.
      As it gives the capability to set many useful setting to the report from the XML configuration file.
      A new extent-config.xml is created as mentined above.
      
    2. Add a method tearDownClass() in the testRunner class to write the report.
    
     	@AfterClass(alwaysRun = true)
      public void tearDownClass() throws Exception {
        Reporter.loadXMLConfig(new File(System.getProperty("user.dir")+ 
        FileReaderManager.getInstance().getConfigReader().getReportConfigPath()));
        Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
        Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
        Reporter.setSystemInfo("Machine", "Mac" + " 64 Bit");
        Reporter.setSystemInfo("Maven", "3.5.2");
        Reporter.setSystemInfo("Java Version", "1.8.0_151");
        testNGCucumberRunner.finish();	
         }
           
Reporting

  Reports for each module are written into their respective /target/cucumber-reports directories after a successful run.
Main Feature

 1.Cucumber framework to test automation Webservices.Due to simple test script architecture, Cucumber provides code            reusability.
 
 2.All classes and methods are implemented in Java with Maven repository to include all dependencies needed. 
 REST-Assured is used to offer a friendly DSL (Domain specific Languages) that describes a connection to an 
 HTTP endpoint and expected results.

2.The framework validates the returned status code, response body, headers and cookies. 
  It can validate each field data type and value. If the returned response includes object of arraylist, 
  the framework can validate its size using the keyword ".size()

3.Can be integrated into DevOps environment to accelerate the delivery process. 
  After each Jenkins deployment, test cases can be executed automatically and 
  the generated XML reports can be passed to Jira to log Defects/Tests automatically.         
  Some configurations needed in Jenkins side.

4.REST-Assured Java API is to test REST webservices and has no direct support for SOAP webservices.
  However, REST-Assured can test SOAP webservices by adding xml request in the body and execute POST HTTP request.

5.Solves the complexity of testing correlated APIs as any test step can use data (body value, header or cookie) received         in the previous steps.

6.It allows the tes
