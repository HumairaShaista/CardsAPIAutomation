package com.api;

import com.utils.ConfigUtil;
import io.restassured.response.Response;

import java.util.Map;

import static com.utils.ConfigUtil.BASE_URI;
import static io.restassured.RestAssured.given;

public class BaseAPI {

    // Generic GET method
    public Response get(String endpoint, Map<String, Object> queryParams) {
        return given()
                .baseUri(BASE_URI)
                .queryParams(queryParams != null ? queryParams : Map.of())
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    // Generic POST method
    public Response post(String endpoint, Object payload) {
        return given()
                .baseUri(BASE_URI)
                .body(payload)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    // Generic PUT method
    public Response put(String endpoint, Object payload) {
        return given()
                .baseUri(BASE_URI)
                .body(payload)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    // Generic DELETE method
    public Response delete(String endpoint) {
        return given()
                .baseUri(BASE_URI)
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }
}

