package com.akv.service;

import com.akv.service.controller.dto.CreateDeviceRequest;
import com.akv.service.controller.dto.UpdateDeviceRequest;
import com.akv.service.domain.Device;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
class DeviceRegistryServiceApplicationTests {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.requestSpecification = given().contentType(ContentType.JSON);
    }

    @Test
    void shouldCreateDevice() {
        CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest("Phone X", "Apple", Device.State.AVAILABLE);
        given()
                .body(createDeviceRequest)
                .when().post("/devices")
                .then().statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo("Phone X"))
                .body("brand", equalTo("Apple"))
                .body("state", equalTo("AVAILABLE"));
    }

    @Test
    void shouldUpdateDevice() {
        long id = createAndGetId("Laptop A", "Dell", Device.State.AVAILABLE);

        UpdateDeviceRequest updateDeviceRequest = new UpdateDeviceRequest("Laptop B", "HP", Device.State.IN_USE);
        given()
                .body(updateDeviceRequest)
                .when().put("/devices/{id}", id)
                .then().statusCode(200)
                .body("name", equalTo("Laptop B"))
                .body("brand", equalTo("HP"))
                .body("state", equalTo("IN_USE"));
    }

    @Test
    void shouldDeleteDevice() {
        long id = createAndGetId("Tablet Z", "Samsung", Device.State.AVAILABLE);

        given().when().delete("/devices/{id}", id)
                .then().statusCode(204);

        given().when().get("/devices/{id}", id)
                .then().statusCode(404);
    }

    @Test
    void shouldFindDeviceById() {
        long id = createAndGetId("Watch S", "Samsung", Device.State.IN_USE);

        given().when().get("/devices/{id}", id)
                .then().statusCode(200)
                .body("id", equalTo((int) id))
                .body("name", equalTo("Watch S"))
                .body("brand", equalTo("Samsung"))
                .body("state", equalTo("IN_USE"));
    }

    private long createAndGetId(String name, String brand, Device.State state) {
        CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest(name, brand, state);
        return given()
                .body(createDeviceRequest)
                .when().post("/devices")
                .then().statusCode(201)
                .extract().jsonPath().getLong("id");
    }

}
