package com.rebirth.mywebstore.exceptions;

public class ResourceNotFoundException extends ResourceException {

    public ResourceNotFoundException(String field, String resourceName, String id, String codeError) {
        super("The resource(%s) with the %s: %s wasn't found".formatted(field, resourceName, id), resourceName, codeError);
    }

    public ResourceNotFoundException(String resourceName, String id, String codeError) {
        super("The resource(%s) with the %s: %s wasn't found".formatted("Id", resourceName, id), resourceName, codeError);
    }
}
