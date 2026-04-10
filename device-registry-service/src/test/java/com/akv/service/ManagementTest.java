package com.akv.service;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class ManagementTest extends BaseFuncTest {

    @Test
    void shouldReturnHealthStatusUp() {
        given().when().get("/actuator/health")
                .then().statusCode(200)
                .body("status", is("UP"));
    }

    @Test
    void shouldExposeSwaggerUi() {
        given().when().get("/swagger-ui/index.html")
                .then().statusCode(200);
    }

    @Test
    void shouldExposeOpenApiSpec() {
        given().when().get("/v3/api-docs")
                .then().statusCode(200)
                .body("info.title", is("Device Registry Service API"))
                .body("info.version", is("1.0.0"))
                .body("paths", notNullValue());
    }

}
