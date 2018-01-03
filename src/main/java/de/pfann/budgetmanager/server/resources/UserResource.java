package de.pfann.budgetmanager.server.resources;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


public class UserResource implements UserApi {


    @Override
    public Response login(@PathParam("username") String aUsername) {
        System.out.println(aUsername);
        Response response = Response.ok()
                .entity("{\"name\" : \"hello\"}")
                .header(Const.ACCESS_CONTROL_ALLOW_ORIGIN_PROPERTY, Const.ACCESS_CONTROL_ALLOW_ORIGIN_VALUE)
                .header(Const.ACCESS_CONTROL_ALLOW_METHODS_PROPERTY, Const.ACCESS_CONTROL_ALLOW_METHODS_VALUE)
                .build();
        return response;
    }

    @Override
    public Response register(@PathParam("username") String aUsername, @PathParam("email") String aEmail) {
        return null;
    }

    @Override
    public Response unregister(@PathParam("username") String aUsername) {
        return null;
    }

    @Override
    public Response activateUser(@PathParam("username") String aUsername) {
        return null;
    }
}
