package com.akv.service.controller;

import com.akv.service.controller.controller.DeviceController;
import com.akv.service.domain.service.DeviceService;
import com.akv.service.domain.Device;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeviceControllerValidationTest extends WebControllerTest {

    @Mock
    DeviceService deviceService;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        mockMvc = buildMockMvc(restDocumentation, new DeviceController(deviceService));
    }

    @Test
    void shouldRejectCreateDeviceWhenNameIsMissing() throws Exception {
        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"brand":"Apple","state":"AVAILABLE"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectCreateDeviceWhenNameIsBlank() throws Exception {
        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"   ","brand":"Apple","state":"AVAILABLE"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectCreateDeviceWhenBrandIsMissing() throws Exception {
        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Phone X","state":"AVAILABLE"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectCreateDeviceWhenStateIsMissing() throws Exception {
        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Phone X","brand":"Apple"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectUpdateDeviceWhenNameIsBlank() throws Exception {
        mockMvc.perform(put("/devices/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"","brand":"Apple","state":"AVAILABLE"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectUpdateDeviceWhenBrandIsMissing() throws Exception {
        mockMvc.perform(put("/devices/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Phone X","state":"AVAILABLE"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectUpdateDeviceWhenStateIsMissing() throws Exception {
        mockMvc.perform(put("/devices/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Phone X","brand":"Apple"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldAcceptPatchDeviceWithPartialFields() throws Exception {
        when(deviceService.patchDevice(any())).thenReturn(Device.builder()
                .id(1L).name("Phone X").brand("Apple")
                .state(Device.State.INACTIVE).creationTime(Instant.now()).build());

        mockMvc.perform(patch("/devices/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"state":"INACTIVE"}
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAcceptPatchDeviceWithNoFields() throws Exception {
        when(deviceService.patchDevice(any())).thenReturn(Device.builder()
                .id(1L).name("Phone X").brand("Apple")
                .state(Device.State.AVAILABLE).creationTime(Instant.now()).build());

        mockMvc.perform(patch("/devices/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

}