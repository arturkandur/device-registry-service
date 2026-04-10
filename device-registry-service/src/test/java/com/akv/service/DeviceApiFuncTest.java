package com.akv.service;

import com.akv.service.controller.dto.DeviceRequest;
import com.akv.service.domain.Device;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class DeviceApiFuncTest extends BaseFuncTest {

    @Test
    void shouldCreateDevice() {
        DeviceRequest deviceRequest = new DeviceRequest("Phone X", "Apple", Device.State.AVAILABLE);
        given()
                .body(deviceRequest)
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

        DeviceRequest updateDeviceRequest = new DeviceRequest("Laptop B", "HP", Device.State.IN_USE);
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

    @Test
    void shouldNotUpdateNameOfInUseDevice() {
        long id = createAndGetId("Phone X", "Apple", Device.State.IN_USE);

        DeviceRequest update = new DeviceRequest("Tablet Z", "Apple", Device.State.IN_USE);
        given()
                .body(update)
                .when().put("/devices/{id}", id)
                .then().statusCode(409);
    }

    @Test
    void shouldNotPatchBrandOfInUseDevice() {
        long id = createAndGetId("Phone X", "Apple", Device.State.IN_USE);

        DeviceRequest patch = new DeviceRequest(null, "Samsung", null);
        given()
                .body(patch)
                .when().patch("/devices/{id}", id)
                .then().statusCode(409);
    }

    @Test
    void shouldNotDeleteInUseDevice() {
        long id = createAndGetId("Phone Y", "Apple", Device.State.IN_USE);

        given().when().delete("/devices/{id}", id)
                .then().statusCode(409);
    }

    private long createAndGetId(String name, String brand, Device.State state) {
        DeviceRequest deviceRequest = new DeviceRequest(name, brand, state);
        return given()
                .body(deviceRequest)
                .when().post("/devices")
                .then().statusCode(201)
                .extract().jsonPath().getLong("id");
    }

}
