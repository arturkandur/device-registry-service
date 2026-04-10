package com.akv.service.logic;

import com.akv.service.domain.Device;
import com.akv.service.domain.exception.DeviceNotDeletableException;
import com.akv.service.domain.exception.DeviceNotUpdatableException;
import com.akv.service.domain.service.DevicePersistenceAdapter;
import com.akv.service.domain.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultDeviceService implements DeviceService {

    private final DevicePersistenceAdapter devicePersistenceAdapter;

    @Override
    @Transactional
    public Device createDevice(Device device) {
        log.debug("Creating device: {}", device);
        Device created = devicePersistenceAdapter.save(device.toBuilder()
                .creationTime(Instant.now())
                .build());
        log.info("Device created: {}", created);
        return created;
    }

    @Override
    @Transactional
    public Device updateDevice(Device device) {
        log.debug("Updating device: {}", device);
        Device existing = devicePersistenceAdapter.findById(device.getId())
                .orElseThrow(() -> new NoSuchElementException("Device not found: " + device.getId()));

        if (existing.getState() == Device.State.IN_USE
                && (!existing.getName().equals(device.getName()) || !existing.getBrand().equals(device.getBrand()))) {
            throw new DeviceNotUpdatableException(device.getId());
        }

        Device updated = devicePersistenceAdapter.save(existing.toBuilder()
                .name(device.getName())
                .brand(device.getBrand())
                .state(device.getState())
                .build());
        log.info("Device updated: {}", updated);
        return updated;
    }

    @Override
    @Transactional
    public Device patchDevice(Device device) {
        log.debug("Patching device: {}", device);
        Device existing = devicePersistenceAdapter.findById(device.getId())
                .orElseThrow(() -> new NoSuchElementException("Device not found: " + device.getId()));

        boolean nameChanging = device.getName() != null && !device.getName().equals(existing.getName());
        boolean brandChanging = device.getBrand() != null && !device.getBrand().equals(existing.getBrand());
        if (existing.getState() == Device.State.IN_USE && (nameChanging || brandChanging)) {
            throw new DeviceNotUpdatableException(device.getId());
        }

        Device.DeviceBuilder deviceBuilder = existing.toBuilder();
        Optional.ofNullable(device.getName()).ifPresent(deviceBuilder::name);
        Optional.ofNullable(device.getBrand()).ifPresent(deviceBuilder::brand);
        Optional.ofNullable(device.getState()).ifPresent(deviceBuilder::state);

        Device patched = devicePersistenceAdapter.save(deviceBuilder.build());
        log.info("Device patched: {}", patched);
        return patched;
    }

    @Override
    @Transactional(readOnly = true)
    public Device getDevice(Long id) {
        log.debug("Fetching device id={}", id);
        Device device = devicePersistenceAdapter.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Device not found: " + id));
        log.info("Device fetched: {}", device);
        return device;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Device> getAllDevices() {
        log.debug("Fetching all devices");
        List<Device> devices = devicePersistenceAdapter.findAll();
        log.info("Devices fetched: count={}", devices.size());
        return devices;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Device> getDevicesByBrand(String brand) {
        log.debug("Fetching devices by brand={}", brand);
        List<Device> devices = devicePersistenceAdapter.findAllByBrand(brand);
        log.info("Devices fetched by brand={}: count={}", brand, devices.size());
        return devices;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Device> getDevicesByState(Device.State state) {
        log.debug("Fetching devices by state={}", state);
        List<Device> devices = devicePersistenceAdapter.findAllByState(state);
        log.info("Devices fetched by state={}: count={}", state, devices.size());
        return devices;
    }

    @Override
    @Transactional
    public void deleteDevice(Long id) {
        log.debug("Deleting device id={}", id);
        Device device = devicePersistenceAdapter.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Device not found: " + id));

        if (device.getState() == Device.State.IN_USE) {
            throw new DeviceNotDeletableException(id);
        }

        devicePersistenceAdapter.deleteById(id);
        log.info("Device deleted id={}", id);
    }

}