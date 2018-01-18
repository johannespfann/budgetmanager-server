package de.pfann.budgetmanager.server.resources;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Logged
@Provider
public class RequestLoggingFilter implements ContainerRequestFilter {


    @Override
    public void filter(final ContainerRequestContext aContainerRequestContext) throws IOException {
        System.out.println(" ##### " + aContainerRequestContext.getMethod());
        System.out.println(" ##### " + aContainerRequestContext.getUriInfo().getPath());
        System.out.println(" ##### " + aContainerRequestContext.getMethod());
    }

}
