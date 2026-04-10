package com.akv.service.domain.exception;

public class DeviceNotDeletableException extends RuntimeException {

    public DeviceNotDeletableException(Long id) {
        super("Device " + id + " cannot be deleted because it is in use");
    }

}
