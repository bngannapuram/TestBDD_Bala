package com.mop.qa.stepdefs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Assert;
import com.cucumber.listener.Reporter;
import com.mop.qa.testbase.TestBase;
import com.mop.qa.util.APIGeneric;
import com.mop.qa.util.SubmitRequest;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ApiStepDefinitions {

	public static com.jayway.restassured.response.Response response;
	public static List<String> idList = new ArrayList<String>();
	public static String accessToken;
	public static String sessionKey;
	public static String assetId;
	public static String api;
	int apiStatusCode;
	
	
	@Given("^I want to execute \"([^\"]*)\" api$")
	public void i_want_to_execute_api(String api) throws Throwable {
		APIGeneric.setAPIDetails(api, TestBase.getEnvName());
	}

	@When("^I execute \"([^\\\"]*)\" get request as per payload$")
	public void i_execute_get_request_as_per_payload_data(String apiName) throws Throwable {
		response = SubmitRequest.get(APIGeneric.setRequest(apiName));
		APIGeneric.printresponse(response, false);
		Reporter.addStepLog(APIGeneric.printresponse(response, false));
	}
	
	@Then("^I validate the correct HTTP <responseCode>$")
	public void i_validate_the_correct_HTTP_responseCode(DataTable dataTable) throws Throwable {
		HashMap<String, String> data = convertDataTableToMap(dataTable).get(0);
		Reporter.addStepLog("Status code is: "+response.getStatusCode());
		Assert.assertEquals("Correct status code is not returned", Integer.parseInt(data.get("responseCode")),
				response.getStatusCode());
	}

	private HashMap<Integer, HashMap<String, String>> convertDataTableToMap(DataTable dataTable){
		List<Map<String,String>> data = dataTable.asMaps(String.class, String.class);
		HashMap<Integer,HashMap<String,String>> hmData = new HashMap<Integer, HashMap<String, String>>();
		for(int i=0;i<data.size();i++) {
			HashMap<String,String>eachData = new HashMap<String, String>();
			Map<String,String> eachMap = data.get(i);
			for (Entry<String, String> entry : eachMap.entrySet()){
				eachData.put(entry.getKey(), entry.getValue());
			}
			hmData.put(i, eachData);
		}
		return hmData;
	}	 
}
