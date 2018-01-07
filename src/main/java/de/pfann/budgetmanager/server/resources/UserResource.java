package de.pfann.budgetmanager.server.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user/")
public class UserResource implements UserApi {

    @GET
    @Path("login/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@PathParam("username") String aUsername) {
        System.out.println(aUsername);
        Response response = Response.ok()
                .entity("{\"name\" : \"hello\"}")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .build();
        return response;
    }

    @POST
    @Path("register/{username}/email/{email}")
    public Response register(
            @PathParam("username") String aUsername,
            @PathParam("email") String aEmail,
            String aBody) {
        // TODO
        System.out.println(aUsername);
        System.out.println(aEmail);
        System.out.println(aBody);

        Response response = Response.ok()
                .entity("{\"name\" : \"hello\"}")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .build();
        return response;
    }


    public Response unregister(@PathParam("username") String aUsername) {
        return null;
    }

    @POST
    @Path("activate/{username}")
    public Response activateUser(
            @PathParam("username") String aUsername,
            String aBody) {
        System.out.println(aUsername);
        System.out.println(aBody);
        Response response = Response.ok()
                .entity("{\"name\" : \"hello\"}")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .build();
        return response;
    }
}
