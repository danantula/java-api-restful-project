package org.ns.automation.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.MediaType;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    //This can be improved by moving to properties file as this changes with each environment
    private static final String BASE_URI = "https://api.restful-api.dev/objects";
    public Response response;
    public Map<String, Object> session = new HashMap<>();

    public RequestSpecification requestSetup() {
        RestAssured.reset();
        return RestAssured.given()
                .baseUri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString());
    }
}
