package com.akv.service.controller.mapper;

import com.akv.service.controller.dto.DeviceResponse;
import com.akv.service.domain.Device;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeviceMapper {

    public static final DeviceMapper DEVICE_MAPPER = Mappers.getMapper(DeviceMapper.class);

    DeviceResponse toResponse(Device device);

}
