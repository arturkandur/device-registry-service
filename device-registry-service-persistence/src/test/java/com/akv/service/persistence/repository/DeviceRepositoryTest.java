package com.akv.service.persistence.repository;

import com.akv.service.persistence.BasePersistenceTest;
import com.akv.service.persistence.entity.DeviceEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DeviceRepositoryTest extends BasePersistenceTest {

    @Autowired
    DeviceRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void shouldSaveAndFindById() {
        DeviceEntity saved = repository.save(entity("Phone X", "Apple", DeviceEntity.State.AVAILABLE));

        assertThat(repository.findById(saved.getId())).isPresent();
    }

    @Test
    void shouldFindAllByBrand() {
        repository.save(entity("Phone X", "Apple", DeviceEntity.State.AVAILABLE));
        repository.save(entity("Watch S", "Apple", DeviceEntity.State.IN_USE));
        repository.save(entity("Laptop A", "Dell", DeviceEntity.State.AVAILABLE));

        List<DeviceEntity> result = repository.findAllByBrand("Apple");

        assertThat(result).hasSize(2);
        assertThat(result).allMatch(e -> e.getBrand().equals("Apple"));
    }

    @Test
    void shouldReturnEmptyListWhenNoBrandMatches() {
        repository.save(entity("Phone X", "Apple", DeviceEntity.State.AVAILABLE));

        List<DeviceEntity> result = repository.findAllByBrand("Samsung");

        assertThat(result).isEmpty();
    }

    @Test
    void shouldFindAllByState() {
        repository.save(entity("Phone X", "Apple", DeviceEntity.State.AVAILABLE));
        repository.save(entity("Watch S", "Samsung", DeviceEntity.State.AVAILABLE));
        repository.save(entity("Laptop A", "Dell", DeviceEntity.State.IN_USE));

        List<DeviceEntity> result = repository.findAllByState(DeviceEntity.State.AVAILABLE);

        assertThat(result).hasSize(2);
        assertThat(result).allMatch(e -> e.getState() == DeviceEntity.State.AVAILABLE);
    }

    @Test
    void shouldReturnEmptyListWhenNoStateMatches() {
        repository.save(entity("Phone X", "Apple", DeviceEntity.State.AVAILABLE));

        List<DeviceEntity> result = repository.findAllByState(DeviceEntity.State.INACTIVE);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldDeleteById() {
        DeviceEntity saved = repository.save(entity("Phone X", "Apple", DeviceEntity.State.AVAILABLE));

        repository.deleteById(saved.getId());

        assertThat(repository.findById(saved.getId())).isEmpty();
    }

    @Test
    void shouldCheckExistsById() {
        DeviceEntity saved = repository.save(entity("Phone X", "Apple", DeviceEntity.State.AVAILABLE));

        assertThat(repository.existsById(saved.getId())).isTrue();
        assertThat(repository.existsById(saved.getId() + 999)).isFalse();
    }

    private DeviceEntity entity(String name, String brand, DeviceEntity.State state) {
        return new DeviceEntity(null, name, brand, state, Instant.now());
    }

}
