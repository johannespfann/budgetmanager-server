package de.pfann.budgetmanager.server.resources;

import de.pfann.budgetmanager.server.util.LogUtil;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Logged
@Provider
public class RequestLoggingFilter implements ContainerRequestFilter {


    @Override
    public void filter(final ContainerRequestContext aContainerRequestContext) throws IOException {
        LogUtil.info(this.getClass(),"Filter Request");
        LogUtil.info(this.getClass(),aContainerRequestContext.getUriInfo().getAbsolutePath().toString());
    }

}
