package com.rebirth.mywebstore.exceptions;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ResourceBadCreationException extends ResourceException {

    private final transient Map<String, Object> errors;

    public ResourceBadCreationException(String resourceName, String codeError, Errors validationErrors) {
        super("This resource has error when you try to create", resourceName, codeError);
        this.errors = validationErrors.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, fieldError -> {
                    Object value = fieldError.getRejectedValue();
                    if (value == null) return "BAD! | " + fieldError.getDefaultMessage();
                    return value + " | " + fieldError.getDefaultMessage();
                }, (s, s2) -> s, ConcurrentHashMap::new));
    }

    public ResourceBadCreationException(String resourceName, String codeError, Map<String, Object> errors) {
        super("This resource has error when you try to create", resourceName, codeError);
        this.errors = errors;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }

}
