package com.rebirth.mywebstore.web.advices;

import java.net.URI;

public class ErrorConstants {

    public static final String APP_NAME = "mystoreweb";
    public static final String PROBLEM_BASE_URL = "http://localhost:8080/tech/problem";
    public static final URI BAD_REQUEST_TYPE = URI.create(PROBLEM_BASE_URL + "/bad-request");
    public static final URI NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/not-found");

    private ErrorConstants() {
    }
}
