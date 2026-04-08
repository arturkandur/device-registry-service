package com.akv.service.controller;

import com.akv.service.controller.dto.CreateDeviceRequest;
import com.akv.service.controller.dto.DeviceResponse;
import com.akv.service.controller.dto.UpdateDeviceRequest;
import com.akv.service.controller.mapper.DeviceMapper;
import com.akv.service.domain.Device;
import com.akv.service.logic.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.akv.service.controller.mapper.DeviceMapper.DEVICE_MAPPER;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {


    private final DeviceService deviceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceResponse createDevice(@RequestBody CreateDeviceRequest request) {
        return DEVICE_MAPPER.toResponse(
                deviceService.createDevice(request.getName(), request.getBrand(), request.getState())
        );
    }

    @PutMapping("/{id}")
    public DeviceResponse updateDevice(@PathVariable Long id, @RequestBody UpdateDeviceRequest request) {
        return DEVICE_MAPPER.toResponse(
                deviceService.updateDevice(id, request.getName(), request.getBrand(), request.getState())
        );
    }

    @PatchMapping("/{id}")
    public DeviceResponse patchDevice(@PathVariable Long id, @RequestBody UpdateDeviceRequest request) {
        return DEVICE_MAPPER.toResponse(
                deviceService.patchDevice(id, request.getName(), request.getBrand(), request.getState())
        );
    }

    @GetMapping("/{id}")
    public DeviceResponse getDevice(@PathVariable Long id) {
        return DEVICE_MAPPER.toResponse(deviceService.getDevice(id));
    }

    @GetMapping
    public List<DeviceResponse> getDevices(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Device.State state
    ) {
        if (brand != null) {
            return deviceService.getDevicesByBrand(brand).stream().map(DEVICE_MAPPER::toResponse).toList();
        }
        if (state != null) {
            return deviceService.getDevicesByState(state).stream().map(DEVICE_MAPPER::toResponse).toList();
        }
        return deviceService.getAllDevices().stream().map(DEVICE_MAPPER::toResponse).toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
    }

}