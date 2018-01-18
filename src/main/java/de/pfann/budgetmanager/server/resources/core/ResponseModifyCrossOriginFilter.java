package de.pfann.budgetmanager.server.resources.core;

import de.pfann.budgetmanager.server.util.LogUtil;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@ModifyCrossOrigin
@Provider
public class ResponseModifyCrossOriginFilter implements ContainerResponseFilter {


    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

    @Override
    public void filter(ContainerRequestContext aContainerRequestContext, ContainerResponseContext aContainerResponseContext) throws IOException {
        LogUtil.info(this.getClass(),"Filter response and manipulate header");
        aContainerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        aContainerResponseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        LogUtil.info(this.getClass(), " - " +  ACCESS_CONTROL_ALLOW_METHODS + "  : " + aContainerResponseContext.getHeaderString(ACCESS_CONTROL_ALLOW_METHODS));
        LogUtil.info(this.getClass(), " - " +  ACCESS_CONTROL_ALLOW_ORIGIN + " : " + aContainerResponseContext.getHeaderString(ACCESS_CONTROL_ALLOW_ORIGIN));
    }
}
