package com.akv.service.controller.controller;

import com.akv.service.controller.dto.DeviceRequest;
import com.akv.service.controller.dto.DeviceResponse;
import com.akv.service.controller.validation.OnCreateOrUpdate;
import com.akv.service.domain.Device;
import com.akv.service.domain.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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

@Slf4j
@RestController
@RequestMapping("/v1/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceResponse createDevice(@RequestBody @Validated(OnCreateOrUpdate.class) DeviceRequest request) {
        log.debug("Creating device: {}", request);
        DeviceResponse response = DEVICE_MAPPER.toResponse(
                deviceService.createDevice(DEVICE_MAPPER.toDomain(request)));
        log.info("Device created: {}", response);
        return response;
    }

    @PutMapping("/{id}")
    public DeviceResponse updateDevice(@PathVariable Long id, @RequestBody @Validated(OnCreateOrUpdate.class) DeviceRequest request) {
        log.debug("Updating device id={}: {}", id, request);
        DeviceResponse response = DEVICE_MAPPER.toResponse(
                deviceService.updateDevice(DEVICE_MAPPER.toDomain(id, request)));
        log.info("Device updated: {}", response);
        return response;
    }

    @PatchMapping("/{id}")
    public DeviceResponse patchDevice(@PathVariable Long id, @RequestBody @Valid DeviceRequest request) {
        log.debug("Patching device id={}: {}", id, request);
        DeviceResponse response = DEVICE_MAPPER.toResponse(
                deviceService.patchDevice(DEVICE_MAPPER.toDomain(id, request)));
        log.info("Device patched: {}", response);
        return response;
    }

    @GetMapping("/{id}")
    public DeviceResponse getDevice(@PathVariable Long id) {
        log.debug("Fetching device id={}", id);
        DeviceResponse response = DEVICE_MAPPER.toResponse(deviceService.getDevice(id));
        log.info("Device fetched: {}", response);
        return response;
    }

    @GetMapping
    public List<DeviceResponse> getDevices(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Device.State state) {
        log.debug("Fetching devices brand={}, state={}", brand, state);
        List<DeviceResponse> response;
        if (brand != null) {
            response = deviceService.getDevicesByBrand(brand).stream().map(DEVICE_MAPPER::toResponse).toList();
        } else if (state != null) {
            response = deviceService.getDevicesByState(state).stream().map(DEVICE_MAPPER::toResponse).toList();
        } else {
            response = deviceService.getAllDevices().stream().map(DEVICE_MAPPER::toResponse).toList();
        }
        log.info("Devices fetched: count={}", response.size());
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDevice(@PathVariable Long id) {
        log.debug("Deleting device id={}", id);
        deviceService.deleteDevice(id);
        log.info("Device deleted id={}", id);
    }

}