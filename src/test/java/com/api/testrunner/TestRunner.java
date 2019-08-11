package com.api.testrunner;

import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.api.managers.FileReaderManager;
import com.vimalselvam.cucumber.listener.ExtentCucumberFormatter;
import com.vimalselvam.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

@CucumberOptions(
		format = {"com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html" }, 
        features = { "Feature" },
        glue = { "com/api/stepdef" }, 
        tags = { "@Smoke" }
)


public class TestRunner {
	
	private TestNGCucumberRunner testNGCucumberRunner;
	public ExtentCucumberFormatter ExtentCucumberFormatter;
	public static String strFolderName;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		FileReaderManager.getInstance();
		strFolderName = System.getProperty("user.dir") + FileReaderManager.getInstance().getConfigReader().getReportPath() + "Run_" + FileReaderManager.getInstance().getGenericClass().getCuttentTimeStamp();
		ExtentCucumberFormatter = new ExtentCucumberFormatter(new File(strFolderName + "//report.html"));
	}

	@Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
	public void feature(CucumberFeatureWrapper cucumberFeature) {
		testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	}

	@DataProvider
	public Object[][] features() {
		return testNGCucumberRunner.provideFeatures();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception {
		Reporter.loadXMLConfig(new File(System.getProperty("user.dir")+ FileReaderManager.getInstance().getConfigReader().getReportConfigPath()));
		Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
		Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
		Reporter.setSystemInfo("Machine", "Mac" + " 64 Bit");
		Reporter.setSystemInfo("Maven", "3.5.2");
		Reporter.setSystemInfo("Java Version", "1.8.0_151");
		testNGCucumberRunner.finish();	
	}

}

