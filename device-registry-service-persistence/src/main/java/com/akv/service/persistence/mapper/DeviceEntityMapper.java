package com.akv.service.persistence.mapper;

import com.akv.service.domain.Device;
import com.akv.service.persistence.entity.DeviceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeviceEntityMapper {

    DeviceEntityMapper INSTANCE = Mappers.getMapper(DeviceEntityMapper.class);

    DeviceEntity toEntity(Device device);

    Device toDomain(DeviceEntity entity);

}
