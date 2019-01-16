package de.pfann.budgetmanager.server.restservices.resources.core;

import de.pfann.budgetmanager.server.restservices.resources.email.EmailDuplicatedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class EmailDublicatedExceptionMapper implements ExceptionMapper<EmailDuplicatedException> {
    @Override
    public Response toResponse(EmailDuplicatedException e) {
        return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
    }
}
