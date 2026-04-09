package com.akv.service.logic;

import com.akv.service.domain.Device;
import com.akv.service.domain.service.DevicePersistenceAdapter;
import com.akv.service.domain.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultDeviceService implements DeviceService {

    private final DevicePersistenceAdapter devicePersistenceAdapter;

    @Override
    public Device createDevice(Device device) {
        return devicePersistenceAdapter.save(device.toBuilder()
                .creationTime(Instant.now())
                .build());
    }

    @Override
    public Device updateDevice(Device device) {
        Device existing = devicePersistenceAdapter.findById(device.getId())
                .orElseThrow(() -> new NoSuchElementException("Device not found: " + device.getId()));
        return devicePersistenceAdapter.save(existing.toBuilder()
                .name(device.getName())
                .brand(device.getBrand())
                .state(device.getState())
                .build());
    }

    @Override
    public Device patchDevice(Device device) {
        Device existing = devicePersistenceAdapter.findById(device.getId())
                .orElseThrow(() -> new NoSuchElementException("Device not found: " + device.getId()));

        Device.DeviceBuilder deviceBuilder = existing.toBuilder();
        Optional.ofNullable(device.getName()).ifPresent(deviceBuilder::name);
        Optional.ofNullable(device.getBrand()).ifPresent(deviceBuilder::brand);
        Optional.ofNullable(device.getState()).ifPresent(deviceBuilder::state);

        return devicePersistenceAdapter.save(deviceBuilder.build());
    }

    @Override
    public Device getDevice(Long id) {
        return devicePersistenceAdapter.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Device not found: " + id));
    }

    @Override
    public List<Device> getAllDevices() {
        return devicePersistenceAdapter.findAll();
    }

    @Override
    public List<Device> getDevicesByBrand(String brand) {
        return devicePersistenceAdapter.findAllByBrand(brand);
    }

    @Override
    public List<Device> getDevicesByState(Device.State state) {
        return devicePersistenceAdapter.findAllByState(state);
    }

    @Override
    public void deleteDevice(Long id) {
        if (!devicePersistenceAdapter.existsById(id)) {
            throw new NoSuchElementException("Device not found: " + id);
        }
        devicePersistenceAdapter.deleteById(id);
    }

}
