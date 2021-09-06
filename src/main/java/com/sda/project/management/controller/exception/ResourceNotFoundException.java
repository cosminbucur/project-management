package com.sda.project.management.controller.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String detailMessage) {
        super(detailMessage);
    }

}
