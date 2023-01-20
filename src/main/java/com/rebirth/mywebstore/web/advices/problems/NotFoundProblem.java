package com.rebirth.mywebstore.web.advices.problems;

import com.rebirth.mywebstore.web.advices.ErrorConstants;
import org.springframework.http.HttpStatus;

public class NotFoundProblem extends BaseProblem {

    public NotFoundProblem(String defaultMessage, String entityName, String errorKey) {
        super(ErrorConstants.NOT_FOUND_TYPE, HttpStatus.NOT_FOUND, defaultMessage, entityName, errorKey);
    }
}
