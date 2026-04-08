package com.akv.service.logic;

import com.akv.service.domain.Device;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceStub implements DeviceService {

    @Override
    public Device createDevice(String name, String brand, Device.State state) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Device updateDevice(Long id, String name, String brand, Device.State state) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Device patchDevice(Long id, String name, String brand, Device.State state) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Device getDevice(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Device> getAllDevices() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Device> getDevicesByBrand(String brand) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Device> getDevicesByState(Device.State state) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void deleteDevice(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
