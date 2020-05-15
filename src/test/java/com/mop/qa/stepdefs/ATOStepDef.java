package com.mop.qa.stepdefs;

import com.mop.qa.pageobject.ATOPage;
import com.mop.qa.testbase.TestBase;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;

public class ATOStepDef extends TestBase {

	public static ATOPage atoPage = null;
	public String url = null, income_yr = null, income_amount = null;

	@Given("^I Launch ATO website$")
	public void i_Launch_ATO_website() throws Throwable {
		String url = "https://www.ato.gov.au/Calculators-and-tools/Host/?anchor=STC&anchor=STC#STC/questions";
		atoPage = new ATOPage();
		atoPage.launchATO(url);
		System.out.println("URL Launched");
	}

	@Given("^I Calculate tax <income_year> <income_amount> <residancy_status>$")
	public void i_Calculate_tax(String income_year, String income_amount, String residancy_status) throws Throwable {
		String income_yr = null;
		String income_amount1 = null;
		String residency_status = null;

		atoPage.calculateTax(income_yr, income_amount1, residency_status);
	}
}
