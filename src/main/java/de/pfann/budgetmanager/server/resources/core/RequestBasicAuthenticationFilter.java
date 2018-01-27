package de.pfann.budgetmanager.server.resources.core;

import de.pfann.budgetmanager.server.util.LogUtil;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Secured
@Provider
public class RequestBasicAuthenticationFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext aContainerRequestContext) throws IOException {
        LogUtil.info(this.getClass(), "------> In Secured");

    }
}
