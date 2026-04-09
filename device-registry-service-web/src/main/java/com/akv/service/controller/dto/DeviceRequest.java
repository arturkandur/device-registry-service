package com.akv.service.controller.dto;

import com.akv.service.domain.Device;
import lombok.Value;

@Value
public class DeviceRequest {

    String name;
    String brand;
    Device.State state;

}
