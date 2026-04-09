package com.akv.service.persistence.mapper;

import com.akv.service.domain.Device;
import com.akv.service.persistence.entity.DeviceEntity;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.akv.service.persistence.mapper.DeviceEntityMapper.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class DeviceEntityMapperTest {

    @Test
    void shouldMapToEntity() {
        Instant now = Instant.now();
        Device device = Device.builder().id(1L).name("Phone X").brand("Apple").state(Device.State.AVAILABLE).creationTime(now).build();

        DeviceEntity entity = INSTANCE.toEntity(device);

        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getName()).isEqualTo("Phone X");
        assertThat(entity.getBrand()).isEqualTo("Apple");
        assertThat(entity.getState()).isEqualTo(DeviceEntity.State.AVAILABLE);
        assertThat(entity.getCreationTime()).isEqualTo(now);
    }

    @Test
    void shouldMapToDomain() {
        Instant now = Instant.now();
        DeviceEntity entity = new DeviceEntity(1L, "Laptop A", "Dell", DeviceEntity.State.IN_USE, now);

        Device device = INSTANCE.toDomain(entity);

        assertThat(device.getId()).isEqualTo(1L);
        assertThat(device.getName()).isEqualTo("Laptop A");
        assertThat(device.getBrand()).isEqualTo("Dell");
        assertThat(device.getState()).isEqualTo(Device.State.IN_USE);
        assertThat(device.getCreationTime()).isEqualTo(now);
    }

}
