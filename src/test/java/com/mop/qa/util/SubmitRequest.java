package com.mop.qa.util;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class SubmitRequest {
	public static Response post(RequestSpecification requestSpec) {
		Response response = null;
		try {
			response = RestAssured.given().spec(requestSpec).config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).post();
		}
		catch(Exception e)
		{
			System.out.println("Exception occured");
		}
		return response;
	}
	
	public static Response put(RequestSpecification requestSpec) {
		Response response = null;
		response = RestAssured.given().spec(requestSpec).config(com.jayway.restassured.RestAssured.config().encoderConfig(com.jayway.restassured.config.EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).put();
		return response;
	}
	
	public static Response get(RequestSpecification requestSpec) {
		Response response = null;	 
		try {
			response = RestAssured.given().spec(requestSpec).get();
		}
		catch(Exception e)
		{
			System.out.println("Exception occured. Retrying again");
			response = RestAssured.given().spec(requestSpec).get();
		}
		return response;
	}
}
