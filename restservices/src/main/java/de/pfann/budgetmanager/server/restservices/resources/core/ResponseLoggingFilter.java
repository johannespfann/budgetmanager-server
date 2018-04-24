package de.pfann.budgetmanager.server.restservices.resources.core;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Logged
@Provider
public class ResponseLoggingFilter implements ContainerResponseFilter{

    @Override
    public void filter(ContainerRequestContext aContainerRequestContext, ContainerResponseContext aContainerResponseContext) throws IOException {

    }

}
