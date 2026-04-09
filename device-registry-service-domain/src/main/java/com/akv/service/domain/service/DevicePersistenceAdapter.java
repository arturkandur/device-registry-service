package com.akv.service.domain.service;

import com.akv.service.domain.Device;

import java.util.List;
import java.util.Optional;

public interface DevicePersistenceAdapter {

    Device save(Device device);

    Optional<Device> findById(Long id);

    List<Device> findAll();

    List<Device> findAllByBrand(String brand);

    List<Device> findAllByState(Device.State state);

    void deleteById(Long id);

    boolean existsById(Long id);

}
