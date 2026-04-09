package com.akv.service.persistence.adapter;

import com.akv.service.domain.Device;
import com.akv.service.persistence.entity.DeviceEntity;
import com.akv.service.persistence.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultDevicePersistenceAdapterTest {

    @Mock
    DeviceRepository jpaRepository;

    @InjectMocks
    DefaultDevicePersistenceAdapter adapter;

    private final Instant now = Instant.now();

    private DeviceEntity entity(Long id) {
        return new DeviceEntity(id, "Phone X", "Apple", DeviceEntity.State.AVAILABLE, now);
    }

    private Device domain(Long id) {
        return Device.builder().id(id).name("Phone X").brand("Apple").state(Device.State.AVAILABLE).creationTime(now).build();
    }

    @Test
    void shouldSaveDevice() {
        when(jpaRepository.save(any())).thenReturn(entity(1L));

        Device result = adapter.save(domain(null));

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Phone X");
        assertThat(result.getBrand()).isEqualTo("Apple");
        assertThat(result.getState()).isEqualTo(Device.State.AVAILABLE);
        assertThat(result.getCreationTime()).isEqualTo(now);
        verify(jpaRepository).save(any());
    }

    @Test
    void shouldFindByIdWhenExists() {
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(entity(1L)));

        Optional<Device> result = adapter.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
    }

    @Test
    void shouldReturnEmptyWhenFindByIdNotFound() {
        when(jpaRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Device> result = adapter.findById(99L);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldFindAllDevices() {
        when(jpaRepository.findAll()).thenReturn(List.of(entity(1L), entity(2L)));

        List<Device> result = adapter.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldFindAllByBrand() {
        when(jpaRepository.findAllByBrand("Apple")).thenReturn(List.of(entity(1L)));

        List<Device> result = adapter.findAllByBrand("Apple");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBrand()).isEqualTo("Apple");
    }

    @Test
    void shouldFindAllByState() {
        when(jpaRepository.findAllByState(DeviceEntity.State.AVAILABLE)).thenReturn(List.of(entity(1L)));

        List<Device> result = adapter.findAllByState(Device.State.AVAILABLE);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getState()).isEqualTo(Device.State.AVAILABLE);
    }

    @Test
    void shouldDeleteById() {
        adapter.deleteById(1L);

        verify(jpaRepository).deleteById(1L);
    }

    @Test
    void shouldReturnTrueWhenExistsById() {
        when(jpaRepository.existsById(1L)).thenReturn(true);

        assertThat(adapter.existsById(1L)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotExistsById() {
        when(jpaRepository.existsById(99L)).thenReturn(false);

        assertThat(adapter.existsById(99L)).isFalse();
    }

}
