# Web/API Test Automation Framework

The framework offers an example for BDD approach an supports Web UI & API automation.

Environment:
Java, Selenium, Cucumber Framework, Maven, TestNG, RestAssured
Also done using Python Selenium (only Web UI testcases)

Testcases: 
1. MLC Insurance - Web UI 
2. ATO Calculator - Web UI
3. Australia Post Calculate Shipping Cose - Web API

# Project Structure

|- src
	|- test
	|- java
		|- com.mop.qa.data
			|- api_test.json		// API endpoints file
			|- com.mop.qa.features
				|- features			// Gherkin feature files
			|- com.mop.qa.pageobject
				|- pageobjects		// pageobject file
			|- com.mop.qa.Stepdefs
				|- stepdefs			// Implementation of cucumber tests
			|- com.mop.qa.testbase
				|- PageBase			// Implements the methods related to the browser actions
				|- TestBase			// implements the methods related to the driver objects
			|- com.mop.qa.testrunner
				|- ExecuteTest		// To run the application, invokes a TestNG class
				|- TestRunner		// Initiates the execution process based on TAGS in xls sheet
				|- RunCakesTest		// collectively executes the feature files and creates a BDD report
			|- com.mop.qa.Util
				|- Util				// Implementation of generic utilities
|- ReportGenerator					// BDD Cucumber HTML Reports
|- data.properties					// Has info related to project data
|- DataSheet.xls					// Input data for test cases (Framework Extension)
|- TestRunnerBDD.xls				// TAG bases test case execution (Framework Extension)

# Starting point of execution

The **ExecuteTest** class is used to trigger the execution of the suite, which calls TestRunner.featureList() from **RunCakesTest** class based on the **TAGS** set in TestRunnerBDD.xls file an cucumber starts its execution reading the feature files.

**Note:
Issue observed in response for GetDeliveryRates API (postman screenshots shared)
