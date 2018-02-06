package de.pfann.budgetmanager.server.resources.core;

import de.pfann.budgetmanager.server.util.LogUtil;

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
        LogUtil.info(this.getClass(),"Filter Response");
        LogUtil.info(this.getClass(), "Status: " + aContainerResponseContext.getStatusInfo().toString());
        //LogUtil.info(this.getClass(), "Entry: " + aContainerResponseContext.getEntity().toString());



    }

}
