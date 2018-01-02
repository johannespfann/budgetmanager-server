package de.pfann.budgetmanager.server.resources;


import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("user/")
public interface UserApi {

    @Path("login/{username}")
    public Response login();

    @Path("register/{username}/password/{password}")
    public Response register();

    @Path("unregister/{username}/password/{")
    public Response unregister();

    @Path("activate/{username}/")
    public Response activateUser();

}
