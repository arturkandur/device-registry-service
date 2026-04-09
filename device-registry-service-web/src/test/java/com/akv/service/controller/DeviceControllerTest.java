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
        return new Device(id, "Phone X", "Apple", Device.State.AVAILABLE, CREATION_TIME);
    }

    @Test
    void createDevice_returns201WithCreatedDevice() throws Exception {
        when(deviceService.createDevice("Phone X", "Apple", Device.State.AVAILABLE))
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
    void updateDevice_returns200WithUpdatedDevice() throws Exception {
        when(deviceService.updateDevice(1L, "Phone X", "Apple", Device.State.IN_USE))
                .thenReturn(new Device(1L, "Phone X", "Apple", Device.State.IN_USE, CREATION_TIME));

        mockMvc.perform(put("/devices/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Phone X","brand":"Apple","state":"IN_USE"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("IN_USE"));
    }

    @Test
    void patchDevice_returns200WithPatchedDevice() throws Exception {
        when(deviceService.patchDevice(1L, null, null, Device.State.INACTIVE))
                .thenReturn(new Device(1L, "Phone X", "Apple", Device.State.INACTIVE, CREATION_TIME));

        mockMvc.perform(patch("/devices/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"state":"INACTIVE"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("INACTIVE"));
    }

    @Test
    void getDevice_returns200WithDevice() throws Exception {
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
    void getDevices_filterByBrand_returns200WithMatchingDevices() throws Exception {
        when(deviceService.getDevicesByBrand("Apple")).thenReturn(List.of(device(1L)));

        mockMvc.perform(get("/devices").param("brand", "Apple"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].brand").value("Apple"));
    }

    @Test
    void getDevices_filterByState_returns200WithMatchingDevices() throws Exception {
        when(deviceService.getDevicesByState(Device.State.AVAILABLE)).thenReturn(List.of(device(1L)));

        mockMvc.perform(get("/devices").param("state", "AVAILABLE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].state").value("AVAILABLE"));
    }

    @Test
    void deleteDevice_returns204() throws Exception {
        doNothing().when(deviceService).deleteDevice(1L);

        mockMvc.perform(delete("/devices/1"))
                .andExpect(status().isNoContent());

        verify(deviceService).deleteDevice(1L);
    }

}
