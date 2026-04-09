package com.akv.service.controller.mapper;

import com.akv.service.controller.dto.DeviceRequest;
import com.akv.service.controller.dto.DeviceResponse;
import com.akv.service.domain.Device;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.akv.service.controller.mapper.DeviceMapper.DEVICE_MAPPER;
import static org.assertj.core.api.Assertions.assertThat;

class DeviceMapperTest {

    @Test
    void shouldMapToDomainWithoutId() {
        DeviceRequest request = new DeviceRequest("Phone X", "Apple", Device.State.AVAILABLE);

        Device device = DEVICE_MAPPER.toDomain(request);

        assertThat(device.getId()).isNull();
        assertThat(device.getName()).isEqualTo("Phone X");
        assertThat(device.getBrand()).isEqualTo("Apple");
        assertThat(device.getState()).isEqualTo(Device.State.AVAILABLE);
        assertThat(device.getCreationTime()).isNull();
    }

    @Test
    void shouldMapToDomainWithId() {
        DeviceRequest request = new DeviceRequest("Laptop A", "Dell", Device.State.IN_USE);

        Device device = DEVICE_MAPPER.toDomain(1L, request);

        assertThat(device.getId()).isEqualTo(1L);
        assertThat(device.getName()).isEqualTo("Laptop A");
        assertThat(device.getBrand()).isEqualTo("Dell");
        assertThat(device.getState()).isEqualTo(Device.State.IN_USE);
        assertThat(device.getCreationTime()).isNull();
    }

    @Test
    void shouldMapToResponse() {
        Instant now = Instant.now();
        Device device = Device.builder().id(1L).name("Watch S").brand("Samsung").state(Device.State.INACTIVE).creationTime(now).build();

        DeviceResponse response = DEVICE_MAPPER.toResponse(device);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Watch S");
        assertThat(response.getBrand()).isEqualTo("Samsung");
        assertThat(response.getState()).isEqualTo(Device.State.INACTIVE);
        assertThat(response.getCreationTime()).isEqualTo(now);
    }

}
