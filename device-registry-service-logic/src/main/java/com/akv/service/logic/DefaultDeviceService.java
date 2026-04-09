package com.akv.service.logic;

import com.akv.service.domain.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DefaultDeviceService implements com.akv.service.domain.service.DeviceService {

    private final com.akv.service.domain.service.DevicePersistenceAdapter devicePersistenceAdapter;

    @Override
    public Device createDevice(String name, String brand, Device.State state) {
        return devicePersistenceAdapter.save(new Device(null, name, brand, state, Instant.now()));
    }

    @Override
    public Device updateDevice(Long id, String name, String brand, Device.State state) {
        Device existing = devicePersistenceAdapter.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Device not found: " + id));
        return devicePersistenceAdapter.save(new Device(id, name, brand, state, existing.getCreationTime()));
    }

    @Override
    public Device patchDevice(Long id, String name, String brand, Device.State state) {
        Device existing = devicePersistenceAdapter.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Device not found: " + id));
        return devicePersistenceAdapter.save(new Device(
                id,
                name != null ? name : existing.getName(),
                brand != null ? brand : existing.getBrand(),
                state != null ? state : existing.getState(),
                existing.getCreationTime()
        ));
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
