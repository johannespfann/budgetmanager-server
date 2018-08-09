package de.pfann.budgetmanager.server.restservices.resources.core;

import de.pfann.budgetmanager.server.restservices.resources.login.AuthenticationManager;

import javax.security.sasl.AuthenticationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Secured
@Provider
public class RequestBasicAuthenticationFilter implements ContainerRequestFilter {

    private AuthenticationManager authenticationManager;
    private static final String AUTH_VALUE_SEPERATOR = ":";

    public RequestBasicAuthenticationFilter(AuthenticationManager aManger){
        authenticationManager = aManger;
    }

    @Override
    public void filter(ContainerRequestContext aContainerRequestContext) throws AuthenticationException, IOException  {
        String authValue = aContainerRequestContext.getHeaderString("Authorization");
        String[] splittedValue = authValue.split(AUTH_VALUE_SEPERATOR);

        String aUsername = splittedValue[0];
        String token = splittedValue[1];


        if(!authenticationManager.isValidToken(aUsername,token)){
            throw new AuthenticationException();
        }
    }

}
