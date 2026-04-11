package com.akv.service.controller.dto;

import com.akv.service.controller.validation.OnCreateOrUpdate;
import com.akv.service.domain.Device;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class DeviceRequest {

    @NotBlank(groups = OnCreateOrUpdate.class)
    String name;

    @NotBlank(groups = OnCreateOrUpdate.class)
    String brand;

    @NotNull(groups = OnCreateOrUpdate.class)
    Device.State state;

}