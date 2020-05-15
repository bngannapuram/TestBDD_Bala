package com.mop.qa.stepdefs;

import com.mop.qa.pageobject.MLCPage;
import com.mop.qa.testbase.TestBase;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;

public class MLCSearchStepDef extends TestBase {

	public static String searchString;
	public static MLCPage mlcPage = null;
	
	@Given("^I Launch MLCInsurance and Request Demo$")
	public void i_Launch_MLCInsurance_and_Request_Demo() throws Throwable {
		String url = "https://www.mlcinsurance.com.au/";
		String name = "Test name";
		String company = "Test company";
		String email = "test@company.com";
		String contact = "0452291888";
		String date = "15-May-2020";
		searchString = "Lifeview";
		
		mlcPage = new MLCPage();
		mlcPage.launchMLC(url);
		System.out.println("URL Launched");

		mlcPage.searchOnWebsite(searchString);
		mlcPage.submitRequestDemoForm(name, company, email, contact, date);
	}
}
