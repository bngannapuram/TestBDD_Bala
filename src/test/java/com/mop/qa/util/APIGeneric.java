package com.mop.qa.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

import com.cucumber.listener.Reporter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
/*import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;*/
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.path.json.JsonPrettifier;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.mop.qa.stepdefs.ApiStepDefinitions;
import com.mop.qa.testbase.TestBase;

public class APIGeneric{

	public static String baseURL;
	public static String path;
	public static String payloadPath;
	public static com.jayway.restassured.response.Response response;
	public static String headers;
	public static String queryParam;
	public static String contentType;

	public static void setAPIDetails(String apiName, String apiEnv) throws JsonProcessingException, IOException {
		System.out.println("apiEnv:" + apiEnv + ".json");
		JsonNode node = getnode(Paths.get(TestBase.getPropertyValue("APIDetailsPath") + apiEnv + ".json"));
		baseURL = node.get(apiName).get("baseURL").asText();
		path = node.get(apiName).get("path").asText();
		payloadPath = node.get(apiName).get("payloadPath").asText();
		
		headers = node.get(apiName).get("headers").asText();
		queryParam = node.get(apiName).get("queryParam").asText();
		contentType = node.get(apiName).get("contentType").asText();
	}

	public static RequestSpecification setRequestForQueryParamNoAuth(HashMap<String, String> queryParam)
			throws JsonProcessingException, IOException, ParseException {
		RestAssured.useRelaxedHTTPSValidation();
		RequestSpecBuilder requestbuilder = new RequestSpecBuilder();
		requestbuilder.setBaseUri(baseURL + path);
		String key = null;
		String value = null;
		for (Entry<String, String> e : queryParam.entrySet()) {
			key = e.getKey();
			value = e.getValue();
			requestbuilder.addQueryParam(key, value);
		}
		RequestSpecification requestSpec = requestbuilder.build();
		return requestSpec;
	}
	
	public static RequestSpecification setRequest(String apiName, String apiKey)
			throws JsonProcessingException, IOException, ParseException {
		RestAssured.useRelaxedHTTPSValidation();
		RequestSpecBuilder requestbuilder = new RequestSpecBuilder();
		requestbuilder.setBaseUri(baseURL + path);
		if(queryParam.equalsIgnoreCase("Yes"))
		{
			Map<String, String> queryParameters = getparameters(apiName, "queryParameters");
			requestbuilder.addQueryParams(queryParameters);
		}
		if(headers.equalsIgnoreCase("Yes"))
		{
			Map<String, String> headerParameters = getparameters(apiName, "headerParameters");
			requestbuilder.addHeaders(headerParameters);
		}
		if(contentType.equalsIgnoreCase("encoded"))
		{
			requestbuilder.setContentType(ContentType.URLENC);
			Map<String, String> encodedParameters = getparameters(apiName, "encodedParameters");
			requestbuilder.addFormParams(encodedParameters);
		}
		RequestSpecification requestSpec = requestbuilder.build();
		return requestSpec;
	}

	public static String printresponse(Response response, boolean inOneLine) {
		String apiResponse;
		if (inOneLine) {
			apiResponse = response.getBody().print();
		} else {
			apiResponse = response.getBody().prettyPrint();
		}
		return apiResponse;
	}

	public static JsonNode getnode(Path path) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(new File(path.toString()));
		return node;
	}
	
	public static Map<String,String> getparameters(String apiName, String keyName) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(new File(Paths.get(TestBase.getPropertyValue("APIDetailsPath") + TestBase.getEnvName() + ".json").toString()));
		Map<String, String> map = mapper.convertValue(node.get(apiName).get(keyName),Map.class);
		return map;
	}
}
