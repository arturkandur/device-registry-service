package com.akv.service.controller.controller;

import com.akv.service.controller.dto.DeviceRequest;
import com.akv.service.controller.dto.DeviceResponse;
import com.akv.service.domain.Device;
import com.akv.service.domain.service.DeviceService;
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

    //TODO: add logs

    private final DeviceService deviceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceResponse createDevice(@RequestBody DeviceRequest request) {
        return DEVICE_MAPPER.toResponse(
                deviceService.createDevice(DEVICE_MAPPER.toDomain(request)));
    }

    @PutMapping("/{id}")
    public DeviceResponse updateDevice(@PathVariable Long id, @RequestBody DeviceRequest request) {
        return DEVICE_MAPPER.toResponse(
                deviceService.updateDevice(DEVICE_MAPPER.toDomain(id, request)));
    }

    @PatchMapping("/{id}")
    public DeviceResponse patchDevice(@PathVariable Long id, @RequestBody DeviceRequest request) {
        return DEVICE_MAPPER.toResponse(
                deviceService.patchDevice(DEVICE_MAPPER.toDomain(id, request)));
    }

    @GetMapping("/{id}")
    public DeviceResponse getDevice(@PathVariable Long id) {
        return DEVICE_MAPPER.toResponse(deviceService.getDevice(id));
    }

    @GetMapping
    public List<DeviceResponse> getDevices(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Device.State state) {
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