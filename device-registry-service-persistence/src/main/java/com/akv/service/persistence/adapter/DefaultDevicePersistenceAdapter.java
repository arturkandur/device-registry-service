package com.akv.service.persistence.adapter;

import com.akv.service.domain.Device;
import com.akv.service.domain.service.DevicePersistenceAdapter;
import com.akv.service.persistence.entity.DeviceEntity;
import com.akv.service.persistence.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.akv.service.persistence.mapper.DeviceEntityMapper.INSTANCE;

@Repository
@RequiredArgsConstructor
public class DefaultDevicePersistenceAdapter implements DevicePersistenceAdapter {

    private final DeviceRepository jpaRepository;

    @Override
    public Device save(Device device) {
        return INSTANCE.toDomain(jpaRepository.save(INSTANCE.toEntity(device)));
    }

    @Override
    public Optional<Device> findById(Long id) {
        return jpaRepository.findById(id).map(INSTANCE::toDomain);
    }

    @Override
    public List<Device> findAll() {
        return jpaRepository.findAll().stream().map(INSTANCE::toDomain).toList();
    }

    @Override
    public List<Device> findAllByBrand(String brand) {
        return jpaRepository.findAllByBrand(brand).stream().map(INSTANCE::toDomain).toList();
    }

    @Override
    public List<Device> findAllByState(Device.State state) {
        DeviceEntity.State entityState = DeviceEntity.State.valueOf(state.name());
        return jpaRepository.findAllByState(entityState).stream().map(INSTANCE::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

}
