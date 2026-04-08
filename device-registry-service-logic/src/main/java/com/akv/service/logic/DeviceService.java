package com.akv.service.logic;

import com.akv.service.domain.Device;

import java.util.List;

public interface DeviceService {

    Device createDevice(String name, String brand, Device.State state);

    Device updateDevice(Long id, String name, String brand, Device.State state);

    Device patchDevice(Long id, String name, String brand, Device.State state);

    Device getDevice(Long id);

    List<Device> getAllDevices();

    List<Device> getDevicesByBrand(String brand);

    List<Device> getDevicesByState(Device.State state);

    void deleteDevice(Long id);

}
