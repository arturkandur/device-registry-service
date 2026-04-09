package com.akv.service.persistence.repository;

import com.akv.service.persistence.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

    List<DeviceEntity> findAllByBrand(String brand);

    List<DeviceEntity> findAllByState(DeviceEntity.State state);

}
