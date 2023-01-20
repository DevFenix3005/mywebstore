package com.rebirth.mywebstore.web.resources;

import com.rebirth.mywebstore.exceptions.ResourceBadCreationException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class BaseResource {

    protected URI generateUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .build(id);
    }

    protected void checkError(Errors errors, String resource) {
        if (errors.hasErrors()) {
            throw new ResourceBadCreationException(resource, resource + "-400", errors);
        }
    }

}
