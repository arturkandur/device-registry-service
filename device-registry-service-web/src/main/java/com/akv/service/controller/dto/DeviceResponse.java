package com.akv.service.controller.dto;

import com.akv.service.domain.Device;
import lombok.Value;

import java.time.Instant;

@Value
public class DeviceResponse {

    Long id;
    String name;
    String brand;
    Device.State state;
    Instant creationTime;

}
