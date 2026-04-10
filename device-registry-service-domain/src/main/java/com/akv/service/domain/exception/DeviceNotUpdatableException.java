package com.akv.service.domain.exception;

public class DeviceNotUpdatableException extends RuntimeException {

    public DeviceNotUpdatableException(Long id) {
        super("Device " + id + " cannot update name or brand because it is in use");
    }
}
