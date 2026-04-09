package com.akv.service.controller.mapper;

import com.akv.service.controller.dto.DeviceRequest;
import com.akv.service.controller.dto.DeviceResponse;
import com.akv.service.domain.Device;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeviceMapper {

    DeviceMapper DEVICE_MAPPER = Mappers.getMapper(DeviceMapper.class);

    DeviceResponse toResponse(Device device);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationTime", ignore = true)
    Device toDomain(DeviceRequest deviceRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "creationTime", ignore = true)
    Device toDomain(Long id, DeviceRequest deviceRequest);

}
