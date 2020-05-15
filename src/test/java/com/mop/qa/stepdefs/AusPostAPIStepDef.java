package com.mop.qa.stepdefs;

import org.junit.Assert;

import com.mop.qa.pageobject.ATOPage;
import com.mop.qa.testbase.TestBase;
import com.mop.qa.util.APIGeneric;
import com.mop.qa.util.SubmitRequest;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;

public class AusPostAPIStepDef extends TestBase {

	String apiKey = "542a2dc6-8cbd-45e4-bda0-d07294bd295e";
	public static com.jayway.restassured.response.Response response;
	
	@Given("^I execute Get Country Codes \"([^\"]*)\" api$")
	public void i_execute_Get_Country_Codes_api(String api) throws Throwable {
		APIGeneric.setAPIDetails(api, TestBase.getEnvName());
		response = SubmitRequest.get(APIGeneric.setRequest(api, apiKey));
		APIGeneric.printresponse(response, false);
		Assert.assertEquals("Correct status code is not returned", 200,
				response.getStatusCode());	
	}

	@Given("^I execute Get Services \"([^\"]*)\" api$")
	public void i_execute_Get_Services_api(String api) throws Throwable {
		APIGeneric.setAPIDetails(api, TestBase.getEnvName());
		response = SubmitRequest.get(APIGeneric.setRequest(api, apiKey));
		APIGeneric.printresponse(response, false);
		Assert.assertEquals("Correct status code is not returned", 200,
				response.getStatusCode());	
	}
	
	@Given("^I execute \"([^\"]*)\" and calculate shipping cost <country_code> <weight> <service_code>$")
	public void i_Calculate_shipping_cost(String api, String country_code, String weight, String service_code) throws Throwable {
		APIGeneric.setAPIDetails(api, TestBase.getEnvName());
		response = SubmitRequest.get(APIGeneric.setRequest(api, apiKey));
		APIGeneric.printresponse(response, false);
		Assert.assertEquals("Correct status code is not returned", 200,
				response.getStatusCode());	
		calculateParcelCharges(country_code, country_code, service_code);
	}
	
	private void calculateParcelCharges(String code, String weight1, String service_code1) {

	}
}
