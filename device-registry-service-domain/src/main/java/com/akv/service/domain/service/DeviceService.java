package com.akv.service.domain.service;

import com.akv.service.domain.Device;

import java.util.List;

public interface DeviceService {

    Device createDevice(Device device);

    Device updateDevice(Device device);

    Device patchDevice(Device device);

    Device getDevice(Long id);

    List<Device> getAllDevices();

    List<Device> getDevicesByBrand(String brand);

    List<Device> getDevicesByState(Device.State state);

    void deleteDevice(Long id);

}
