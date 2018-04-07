package de.pfann.budgetmanager.server.core.resources.core;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Secured
@Provider
public class RequestBasicAuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext aContainerRequestContext) throws IOException {

    }

}
