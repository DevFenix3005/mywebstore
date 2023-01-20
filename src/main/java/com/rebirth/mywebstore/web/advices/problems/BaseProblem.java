package com.rebirth.mywebstore.web.advices.problems;

import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.Map;

public class BaseProblem {

    private final URI type;
    private final HttpStatus httpStatus;
    private final String message;
    private final String entityName;
    private final String errorKey;

    private Map<String, Object> errors;

    public BaseProblem(URI type, HttpStatus httpStatus, String message, String entityName, String errorKey) {
        this.type = type;
        this.httpStatus = httpStatus;
        this.message = message;
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    public URI getType() {
        return type;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return errorKey;
    }


    public Map<String, Object> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Object> errors) {
        this.errors = errors;
    }
}

