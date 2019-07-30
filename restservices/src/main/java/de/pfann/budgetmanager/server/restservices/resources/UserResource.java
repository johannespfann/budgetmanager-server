package de.pfann.budgetmanager.server.restservices.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.pfann.budgetmanager.server.model.User;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;
import de.pfann.budgetmanager.server.restservices.resources.core.Secured;
import de.pfann.budgetmanager.server.restservices.resources.util.UserJsonMapper;

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
            @HeaderParam("Authorization") String authorizationValue) throws JsonProcessingException {
        return userResourceFacade.login(aAccessor,authorizationValue);
    }

    @POST
    @Logged
    @CrossOriginFilter
    @Path("register/{username}/email/{email}")
    public String register(
            @PathParam("username") String aUsername,
            @PathParam("email") String aEmail,
            String aBody) {
        String newUsername = userResourceFacade.register(aUsername,aEmail,getPassword(aBody));
        return "{\"name\" : \"" + newUsername + "\"}";
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

    @GET
    @Logged
    @Secured
    @CrossOriginFilter
    @Produces(MediaType.APPLICATION_JSON)
    @Path("info/{username}")
    public String getUserInfomation(@PathParam("username") String aUsername){
        User user =  userResourceFacade.getUserInfomation(aUsername);
        return UserJsonMapper.convertToJson(user);

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
