package com.akv.service.domain;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder(toBuilder = true)
public class Device {

    Long id;
    String name;
    String brand;
    State state;
    Instant creationTime;

    public enum State {
        AVAILABLE, IN_USE, INACTIVE
    }

}
