package com.sda.project.management.controller.exception;

public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String detailMessage) {
        super(detailMessage);
    }

}
