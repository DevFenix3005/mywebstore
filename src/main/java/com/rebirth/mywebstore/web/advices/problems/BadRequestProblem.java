package com.rebirth.mywebstore.web.advices.problems;

import com.rebirth.mywebstore.web.advices.ErrorConstants;
import org.springframework.http.HttpStatus;

public class BadRequestProblem extends BaseProblem {
    public BadRequestProblem(String defaultMessage, String entityName, String errorKey) {
        super(ErrorConstants.BAD_REQUEST_TYPE, HttpStatus.BAD_REQUEST, defaultMessage, entityName, errorKey);
    }
}
