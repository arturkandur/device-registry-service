package com.akv.service.logic;

import com.akv.service.domain.Device;
import com.akv.service.domain.service.DevicePersistenceAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import com.akv.service.domain.exception.DeviceNotDeletableException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultDeviceServiceTest {

    @Mock
    DevicePersistenceAdapter persistenceAdapter;

    @InjectMocks
    DefaultDeviceService service;

    @Test
    void shouldCreateDevice() {
        Device input = Device.builder().name("Phone X").brand("Apple").state(Device.State.AVAILABLE).build();
        Device saved = input.toBuilder().id(1L).creationTime(Instant.now()).build();
        when(persistenceAdapter.save(any())).thenReturn(saved);

        Device result = service.createDevice(input);

        assertThat(result).isEqualTo(saved);
        verify(persistenceAdapter).save(any());
    }

    @Test
    void shouldSetCreationTimeOnCreate() {
        Device input = Device.builder().name("Phone X").brand("Apple").state(Device.State.AVAILABLE).build();
        when(persistenceAdapter.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Device result = service.createDevice(input);

        assertThat(result.getCreationTime()).isNotNull();
    }

    @Test
    void shouldUpdateDevice() {
        Instant creationTime = Instant.now();
        Device existing = Device.builder().id(1L).name("Old").brand("Old").state(Device.State.AVAILABLE).creationTime(creationTime).build();
        Device update = Device.builder().id(1L).name("New").brand("New").state(Device.State.IN_USE).build();
        when(persistenceAdapter.findById(1L)).thenReturn(Optional.of(existing));
        when(persistenceAdapter.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Device result = service.updateDevice(update);

        assertThat(result.getName()).isEqualTo("New");
        assertThat(result.getBrand()).isEqualTo("New");
        assertThat(result.getState()).isEqualTo(Device.State.IN_USE);
        assertThat(result.getCreationTime()).isEqualTo(creationTime);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingDevice() {
        Device update = Device.builder().id(99L).name("X").brand("X").state(Device.State.AVAILABLE).build();
        when(persistenceAdapter.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateDevice(update))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("99");
    }

    @Test
    void shouldPatchDeviceOnlyNonNullFields() {
        Instant creationTime = Instant.now();
        Device existing = Device.builder().id(1L).name("Old").brand("OldBrand").state(Device.State.AVAILABLE).creationTime(creationTime).build();
        Device patch = Device.builder().id(1L).name("New").build();
        when(persistenceAdapter.findById(1L)).thenReturn(Optional.of(existing));
        when(persistenceAdapter.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Device result = service.patchDevice(patch);

        assertThat(result.getName()).isEqualTo("New");
        assertThat(result.getBrand()).isEqualTo("OldBrand");
        assertThat(result.getState()).isEqualTo(Device.State.AVAILABLE);
    }

    @Test
    void shouldThrowWhenPatchingNonExistingDevice() {
        Device patch = Device.builder().id(99L).name("X").build();
        when(persistenceAdapter.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.patchDevice(patch))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("99");
    }

    @Test
    void shouldGetDevice() {
        Device device = Device.builder().id(1L).name("Phone X").brand("Apple").state(Device.State.AVAILABLE).creationTime(Instant.now()).build();
        when(persistenceAdapter.findById(1L)).thenReturn(Optional.of(device));

        Device result = service.getDevice(1L);

        assertThat(result).isEqualTo(device);
    }

    @Test
    void shouldThrowWhenGettingNonExistingDevice() {
        when(persistenceAdapter.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getDevice(99L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("99");
    }

    @Test
    void shouldGetAllDevices() {
        List<Device> devices = List.of(
                Device.builder().id(1L).name("Phone X").brand("Apple").state(Device.State.AVAILABLE).creationTime(Instant.now()).build(),
                Device.builder().id(2L).name("Laptop A").brand("Dell").state(Device.State.IN_USE).creationTime(Instant.now()).build()
        );
        when(persistenceAdapter.findAll()).thenReturn(devices);

        List<Device> result = service.getAllDevices();

        assertThat(result).isEqualTo(devices);
    }

    @Test
    void shouldGetDevicesByBrand() {
        List<Device> devices = List.of(
                Device.builder().id(1L).name("Phone X").brand("Apple").state(Device.State.AVAILABLE).creationTime(Instant.now()).build()
        );
        when(persistenceAdapter.findAllByBrand("Apple")).thenReturn(devices);

        List<Device> result = service.getDevicesByBrand("Apple");

        assertThat(result).isEqualTo(devices);
    }

    @Test
    void shouldGetDevicesByState() {
        List<Device> devices = List.of(
                Device.builder().id(1L).name("Phone X").brand("Apple").state(Device.State.AVAILABLE).creationTime(Instant.now()).build()
        );
        when(persistenceAdapter.findAllByState(Device.State.AVAILABLE)).thenReturn(devices);

        List<Device> result = service.getDevicesByState(Device.State.AVAILABLE);

        assertThat(result).isEqualTo(devices);
    }

    @Test
    void shouldDeleteDevice() {
        Device device = Device.builder().id(1L).name("Phone").brand("Apple").state(Device.State.AVAILABLE).creationTime(Instant.now()).build();
        when(persistenceAdapter.findById(1L)).thenReturn(Optional.of(device));

        service.deleteDevice(1L);

        verify(persistenceAdapter).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingDevice() {
        when(persistenceAdapter.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteDevice(99L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("99");

        verify(persistenceAdapter, never()).deleteById(any());
    }

    @Test
    void shouldThrowWhenDeletingInUseDevice() {
        Device device = Device.builder().id(2L).name("Laptop").brand("Dell").state(Device.State.IN_USE).creationTime(Instant.now()).build();
        when(persistenceAdapter.findById(2L)).thenReturn(Optional.of(device));

        assertThatThrownBy(() -> service.deleteDevice(2L))
                .isInstanceOf(DeviceNotDeletableException.class)
                .hasMessageContaining("2");

        verify(persistenceAdapter, never()).deleteById(any());
    }

}
