package com.akv.service.controller;

import com.akv.service.controller.controller.DeviceController;
import com.akv.service.domain.Device;
import com.akv.service.domain.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DeviceControllerTest {

    @Mock
    DeviceService deviceService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DeviceController(deviceService)).build();
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
        when(deviceService.createDevice(device))
                .thenReturn(device(1L));

        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Phone X","brand":"Apple","state":"AVAILABLE"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Phone X"))
                .andExpect(jsonPath("$.brand").value("Apple"))
                .andExpect(jsonPath("$.state").value("AVAILABLE"));
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
                .thenReturn(device.toBuilder().id(1L).creationTime(CREATION_TIME).build());

        mockMvc.perform(put("/devices/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Phone X","brand":"Apple","state":"IN_USE"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("IN_USE"));
    }

    @Test
    void shouldPatchDeviceAndReturns200WithPatchedDevice() throws Exception {
        Device.builder().state(Device.State.INACTIVE).build();
        when(deviceService.patchDevice(Device.builder().id(1L).state(Device.State.INACTIVE).build()))
                .thenReturn(device(1L).toBuilder().state(Device.State.INACTIVE).build());

        mockMvc.perform(patch("/devices/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"state":"INACTIVE"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("INACTIVE"));
    }

    @Test
    void shouldGetDeviceAndReturns200WithDevice() throws Exception {
        when(deviceService.getDevice(1L)).thenReturn(device(1L));

        mockMvc.perform(get("/devices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Phone X"));
    }

    @Test
    void getAllDevices_returns200WithDeviceList() throws Exception {
        when(deviceService.getAllDevices()).thenReturn(List.of(device(1L), device(2L)));

        mockMvc.perform(get("/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldGetDevicesAndFilterByBrandAndReturns200WithMatchingDevices() throws Exception {
        when(deviceService.getDevicesByBrand("Apple")).thenReturn(List.of(device(1L)));

        mockMvc.perform(get("/devices").param("brand", "Apple"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].brand").value("Apple"));
    }

    @Test
    void shouldGetDevicesAndFilterByStateAndReturns200WithMatchingDevices() throws Exception {
        when(deviceService.getDevicesByState(Device.State.AVAILABLE)).thenReturn(List.of(device(1L)));

        mockMvc.perform(get("/devices").param("state", "AVAILABLE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].state").value("AVAILABLE"));
    }

    @Test
    void shouldDeleteDeviceAndReturns204() throws Exception {
        doNothing().when(deviceService).deleteDevice(1L);

        mockMvc.perform(delete("/devices/1"))
                .andExpect(status().isNoContent());

        verify(deviceService).deleteDevice(1L);
    }

}
