package de.pfann.budgetmanager.server.restservices.resources;


import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;
import de.pfann.budgetmanager.server.restservices.resources.core.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("user/")
public class UserResource {

    private UserResourceFacade userResourceFacade;

    public UserResource(UserResourceFacade aUserResourceFacade){
        userResourceFacade = aUserResourceFacade;
    }

    @POST
    @CrossOriginFilter
    @Secured
    @Logged
    @Path("logout/{accessor}")
    public void logout(@PathParam("accessor") String aAccessor) {
        userResourceFacade.logout(aAccessor, getAccessCode("placeholder"));
    }

    @GET
    @Logged
    @CrossOriginFilter
    @Path("login/{accessor}")
    @Produces(MediaType.TEXT_PLAIN)
    public String login(
            @PathParam("accessor") String aAccessor,
            @HeaderParam("Authorization") String authorizationValue) {
        return userResourceFacade.login(aAccessor,authorizationValue);
    }

    @POST
    @Logged
    @CrossOriginFilter
    @Path("register/{username}/email/{email}")
    public void register(
            @PathParam("username") String aUsername,
            @PathParam("email") String aEmail,
            String aBody) {
        userResourceFacade.register(aUsername,aEmail,getPassword(aBody));
    }

    @POST
    @Logged
    @CrossOriginFilter
    @Path("activate/resendemail/username/{username}/email{email}")
    public void resendEmail(
            @PathParam("username") String aUsername,
            @PathParam("email") String aEmail,
            String aBody){
        userResourceFacade.resendEmail(aUsername,aEmail);
    }

    @POST
    @Logged
    @CrossOriginFilter
    @Path("activate/{username}")
    public void activateUser(
            @PathParam("username") String aUsername,
            String aBody) {
        userResourceFacade.activeUser(aUsername,getActivationCode(aBody));
    }

    private String getActivationCode(String aBody) {
        return aBody;
    }

    private String getPassword(String aBody) {
        return aBody;
    }

    private String getAccessCode(String aBody) {
        return aBody;
    }

}
