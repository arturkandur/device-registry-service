package com.akv.service.controller.dto;

import com.akv.service.domain.Device;
import lombok.Value;

@Value
public class UpdateDeviceRequest {

    String name;
    String brand;
    Device.State state;

}
