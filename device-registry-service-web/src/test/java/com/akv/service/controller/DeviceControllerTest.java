package com.akv.service.controller;

import com.akv.service.controller.controller.DeviceController;
import com.akv.service.domain.Device;
import com.akv.service.domain.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeviceControllerTest extends WebControllerTest {

    @Mock
    DeviceService deviceService;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        mockMvc = buildMockMvc(restDocumentation, new DeviceController(deviceService));
    }

    private static final Instant CREATION_TIME = Instant.parse("2026-01-01T00:00:00Z");

    private Device device(Long id) {
        return Device.builder()
                .id(id)
                .name("Phone X")
                .brand("Apple")
                .state(Device.State.AVAILABLE)
                .creationTime(CREATION_TIME)
                .build();
    }

    @Test
    void shouldCreateDeviceAndReturns201WithCreatedDevice() throws Exception {
        Device device = Device.builder()
                .name("Phone X")
                .brand("Apple")
                .state(Device.State.AVAILABLE)
                .build();
        when(deviceService.createDevice(device)).thenReturn(device(1L));

        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Phone X","brand":"Apple","state":"AVAILABLE"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Phone X"))
                .andExpect(jsonPath("$.brand").value("Apple"))
                .andExpect(jsonPath("$.state").value("AVAILABLE"))
                .andDo(document("devices/create",
                        requestFields(
                                fieldWithPath("name").description("Device name"),
                                fieldWithPath("brand").description("Device brand"),
                                fieldWithPath("state").description("Device state: AVAILABLE, IN_USE, or INACTIVE")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Unique device identifier"),
                                fieldWithPath("name").description("Device name"),
                                fieldWithPath("brand").description("Device brand"),
                                fieldWithPath("state").description("Device state"),
                                fieldWithPath("creationTime").description("Timestamp when the device was registered")
                        )
                ));
    }

    @Test
    void shouldUpdateDeviceAndReturns200WithUpdatedDevice() throws Exception {
        Device device = Device.builder()
                .id(1L)
                .name("Phone X")
                .brand("Apple")
                .state(Device.State.IN_USE)
                .build();
        when(deviceService.updateDevice(device))
                .thenReturn(device.toBuilder().creationTime(CREATION_TIME).build());

        mockMvc.perform(put("/devices/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Phone X","brand":"Apple","state":"IN_USE"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("IN_USE"))
                .andDo(document("devices/update",
                        pathParameters(
                                parameterWithName("id").description("Device ID")
                        ),
                        requestFields(
                                fieldWithPath("name").description("Device name"),
                                fieldWithPath("brand").description("Device brand"),
                                fieldWithPath("state").description("Device state: AVAILABLE, IN_USE, or INACTIVE")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Unique device identifier"),
                                fieldWithPath("name").description("Device name"),
                                fieldWithPath("brand").description("Device brand"),
                                fieldWithPath("state").description("Device state"),
                                fieldWithPath("creationTime").description("Timestamp when the device was registered")
                        )
                ));
    }

    @Test
    void shouldPatchDeviceAndReturns200WithPatchedDevice() throws Exception {
        when(deviceService.patchDevice(Device.builder().id(1L).state(Device.State.INACTIVE).build()))
                .thenReturn(device(1L).toBuilder().state(Device.State.INACTIVE).build());

        mockMvc.perform(patch("/devices/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"state":"INACTIVE"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("INACTIVE"))
                .andDo(document("devices/patch",
                        pathParameters(
                                parameterWithName("id").description("Device ID")
                        ),
                        requestFields(
                                fieldWithPath("name").description("Device name (optional)").type(String.class).optional(),
                                fieldWithPath("brand").description("Device brand (optional)").type(String.class).optional(),
                                fieldWithPath("state").description("Device state (optional): AVAILABLE, IN_USE, or INACTIVE").optional()
                        ),
                        responseFields(
                                fieldWithPath("id").description("Unique device identifier"),
                                fieldWithPath("name").description("Device name"),
                                fieldWithPath("brand").description("Device brand"),
                                fieldWithPath("state").description("Device state"),
                                fieldWithPath("creationTime").description("Timestamp when the device was registered")
                        )
                ));
    }

    @Test
    void shouldGetDeviceAndReturns200WithDevice() throws Exception {
        when(deviceService.getDevice(1L)).thenReturn(device(1L));

        mockMvc.perform(get("/devices/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Phone X"))
                .andDo(document("devices/get-by-id",
                        pathParameters(
                                parameterWithName("id").description("Device ID")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Unique device identifier"),
                                fieldWithPath("name").description("Device name"),
                                fieldWithPath("brand").description("Device brand"),
                                fieldWithPath("state").description("Device state"),
                                fieldWithPath("creationTime").description("Timestamp when the device was registered")
                        )
                ));
    }

    @Test
    void shouldGetAllDevicesAndReturns200WithDeviceList() throws Exception {
        when(deviceService.getAllDevices()).thenReturn(List.of(device(1L), device(2L)));

        mockMvc.perform(get("/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(document("devices/get-all",
                        responseFields(
                                fieldWithPath("[].id").description("Unique device identifier"),
                                fieldWithPath("[].name").description("Device name"),
                                fieldWithPath("[].brand").description("Device brand"),
                                fieldWithPath("[].state").description("Device state"),
                                fieldWithPath("[].creationTime").description("Timestamp when the device was registered")
                        )
                ));
    }

    @Test
    void shouldGetDevicesAndFilterByBrandAndReturns200WithMatchingDevices() throws Exception {
        when(deviceService.getDevicesByBrand("Apple")).thenReturn(List.of(device(1L)));

        mockMvc.perform(get("/devices").param("brand", "Apple"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].brand").value("Apple"))
                .andDo(document("devices/get-by-brand",
                        queryParameters(
                                parameterWithName("brand").description("Filter devices by brand")
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("Unique device identifier"),
                                fieldWithPath("[].name").description("Device name"),
                                fieldWithPath("[].brand").description("Device brand"),
                                fieldWithPath("[].state").description("Device state"),
                                fieldWithPath("[].creationTime").description("Timestamp when the device was registered")
                        )
                ));
    }

    @Test
    void shouldGetDevicesAndFilterByStateAndReturns200WithMatchingDevices() throws Exception {
        when(deviceService.getDevicesByState(Device.State.AVAILABLE)).thenReturn(List.of(device(1L)));

        mockMvc.perform(get("/devices").param("state", "AVAILABLE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].state").value("AVAILABLE"))
                .andDo(document("devices/get-by-state",
                        queryParameters(
                                parameterWithName("state").description("Filter devices by state: AVAILABLE, IN_USE, or INACTIVE")
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("Unique device identifier"),
                                fieldWithPath("[].name").description("Device name"),
                                fieldWithPath("[].brand").description("Device brand"),
                                fieldWithPath("[].state").description("Device state"),
                                fieldWithPath("[].creationTime").description("Timestamp when the device was registered")
                        )
                ));
    }

    @Test
    void shouldDeleteDeviceAndReturns204() throws Exception {
        doNothing().when(deviceService).deleteDevice(1L);

        mockMvc.perform(delete("/devices/{id}", 1L))
                .andExpect(status().isNoContent())
                .andDo(document("devices/delete",
                        pathParameters(
                                parameterWithName("id").description("Device ID")
                        )
                ));

        verify(deviceService).deleteDevice(1L);
    }

}
