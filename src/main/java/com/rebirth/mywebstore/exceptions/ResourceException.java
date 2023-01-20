package com.rebirth.mywebstore.exceptions;

public class ResourceException extends RuntimeException {

    private final String resourceName;
    private final String codeError;


    public ResourceException(String message, String resourceName, String codeError) {
        super(message);
        this.resourceName = resourceName;
        this.codeError = codeError;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getCodeError() {
        return codeError;
    }
}
