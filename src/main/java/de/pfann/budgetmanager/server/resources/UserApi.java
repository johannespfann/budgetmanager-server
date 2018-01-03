package de.pfann.budgetmanager.server.resources;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("user/")
public interface UserApi {

    /**
     *
     * @param aUsername
     * @return
     */
    @POST
    @Path("login/{username}")
    public Response login(
            @PathParam("username") String aUsername);

    /**
     *
     * @param aUsername
     * @param aEmail
     * @return
     */
    @Path("register/{username}/email/{email}")
    public Response register(
            @PathParam("username") String aUsername,
            @PathParam("email") String aEmail);


    @Path("unregister/{username}")
    public Response unregister(
            @PathParam("username") String aUsername);

    @Path("activate/{username}")
    public Response activateUser(
            @PathParam("username") String aUsername);

}
