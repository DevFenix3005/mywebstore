package com.rebirth.mywebstore.web.advices;

import com.rebirth.mywebstore.exceptions.ResourceBadCreationException;
import com.rebirth.mywebstore.exceptions.ResourceNotFoundException;
import com.rebirth.mywebstore.web.advices.problems.BadRequestProblem;
import com.rebirth.mywebstore.web.advices.problems.BaseProblem;
import com.rebirth.mywebstore.web.advices.problems.NotFoundProblem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ResourcesAdvices extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceBadCreationException.class)
    public ResponseEntity userBadRequest(ResourceBadCreationException userResourceBadCreation, NativeWebRequest request) {
        BadRequestProblem userBadRequestProblem = new BadRequestProblem(
                userResourceBadCreation.getMessage(),
                userResourceBadCreation.getResourceName(),
                userResourceBadCreation.getCodeError()
        );
        userBadRequestProblem.setErrors(userResourceBadCreation.getErrors());
        HttpHeaders headers = createHeaders(userBadRequestProblem);
        return this.handleExceptionInternal(userResourceBadCreation, userBadRequestProblem, headers, userBadRequestProblem.getHttpStatus(), request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity userNotFound(ResourceNotFoundException userResourceNotFound, NativeWebRequest request) {
        NotFoundProblem userNotFoundProblem = new NotFoundProblem(
                userResourceNotFound.getMessage(),
                userResourceNotFound.getResourceName(),
                userResourceNotFound.getCodeError()
        );
        HttpHeaders headers = createHeaders(userNotFoundProblem);
        return this.handleExceptionInternal(userResourceNotFound, userNotFoundProblem, headers, userNotFoundProblem.getHttpStatus(), request);
    }


    private HttpHeaders createHeaders(BaseProblem problem) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("app", ErrorConstants.APP_NAME);
        headers.set("entityName", problem.getEntityName());
        headers.set("errorKey", problem.getErrorKey());
        headers.set("message", problem.getMessage());
        if (problem.getErrors() != null) {
            for (Map.Entry<String, Object> objectEntry : problem.getErrors().entrySet()) {
                String key = objectEntry.getKey();
                Object value = objectEntry.getValue();
                headers.set(key, value.toString());
            }
        }
        return headers;
    }

}
