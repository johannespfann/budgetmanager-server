package de.pfann.budgetmanager.server.restservices.resources;


import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.model.AppUser;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;
import de.pfann.budgetmanager.server.restservices.resources.core.Secured;
import de.pfann.budgetmanager.server.restservices.resources.email.EmailService;
import de.pfann.budgetmanager.server.restservices.resources.login.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user/")
public class UserResource {

    private AppUserFacade userFacade;

    private EmailService emailService;

    public UserResource(){
        userFacade = new AppUserFacade();
        emailService = new EmailService();
    }

    @POST
    @CrossOriginFilter
    @Secured
    @Logged
    @Path("logout/{accessor}")
    public Response logout(
            @PathParam("accessor") String aAccessor
            ) {

        String accessCode = getAccessCode("placeholder");

        AppUser user = userFacade.getUserByNameOrEmail(aAccessor);

        LogUtil.info(this.getClass(), "Unregister user " + user.getName());
        AccessPool.getInstance().unregister(user, accessCode);

        LogUtil.info(this.getClass(), "Send request ...");
        return Response.ok()
                .entity("{\"username\" : \""+user.getName()+"\"}")
                .build();
    }

    private String getAccessCode(String aBody) {
        return aBody;
    }

    @GET
    @Logged
    @CrossOriginFilter
    @Path("login/{accessor}")
    @Produces(MediaType.TEXT_PLAIN)
    public String login(
            @PathParam("accessor") String aAccessor,
            @HeaderParam("Authorization") String authorizationValue) {

        LogUtil.info(this.getClass(),"Authorization: " + authorizationValue);
        AppUser user = userFacade.getUserByNameOrEmail(aAccessor);
        LogUtil.info(this.getClass(),"Found user: " + user.getName());

        LogUtil.info(this.getClass(),"user    : " + LoginUtil.extractUser(authorizationValue));
        LogUtil.info(this.getClass(),"password: " + LoginUtil.extractPassword(authorizationValue));

        String username = LoginUtil.extractUser(authorizationValue);
        String password = LoginUtil.extractPassword(authorizationValue);

        if(user == null){
            LogUtil.info(this.getClass(),"user was null");
            throw new SecurityException();
        }

        if(!user.isActivated()){
            LogUtil.info(this.getClass(),"user was not activated");
            throw new SecurityException();
        }

        if(!user.getName().equals(username)){
            LogUtil.info(this.getClass(),"user was not the same: " + user.getName() + " and " + username);
            throw new SecurityException();
        }

        if(!user.getPassword().equals(password)){
            LogUtil.info(this.getClass(),"password was wrong: " + user.getPassword() + " and " + password);
            throw new SecurityException();
        }


        String JSON = "{\"accesstoken\" : \"" + LoginUtil.getAccessTocken() +"\"," +
                "\"username\" : \"" + user.getName() +"\"," +
                "\"email\" : \"" + user.getEmail() +"\" }";
        LogUtil.info(this.getClass(),"Return JSON " + JSON);
        return JSON;
    }

    @POST
    @Logged
    @CrossOriginFilter
    @Path("register/{username}/email/{email}")
    public Response register(
            @PathParam("username") String aUsername,
            @PathParam("email") String aEmail,
            String aBody) {

        AppUser user = new AppUser();
        user.setName(LoginUtil.getUserNameWithUnique(aUsername));
        user.setEmail(aEmail);
        user.setPassword(getPassword(aBody));

        userFacade.createNewUser(user);

        String activationCode = LoginUtil.getActivationCode();

        try {
            ActivationPool.create().addActivationTicket(user.getName(),aEmail,activationCode);
        } catch (ActivationCodeAlreadyExistsException e) {
            e.printStackTrace();
        }

        emailService.sendActivationEmail(user.getName(),aEmail,activationCode);

        return Response.ok()
                .entity("{\"username\" : \""+user.getName()+"\"}")
                .build();
    }

    private String getPassword(String aBody) {
        return aBody;
    }

    @POST
    @Logged
    @CrossOriginFilter
    @Path("activate/resendemail/username/{username}/email{email}")
    public Response resendEmail(
            @PathParam("username") String aUsername,
            @PathParam("email") String aEmail,
            String aBody){

        String activationCode = LoginUtil.getActivationCode();

        try {
            ActivationPool.create().addActivationTicket(aUsername,aEmail,activationCode);
        } catch (ActivationCodeAlreadyExistsException e) {
            e.printStackTrace();
        }

        emailService.sendActivationEmail(aUsername,aEmail,activationCode);

        return Response.ok()
                .entity("{\"username\" : \""+aUsername+"\"}")
                .build();
    }

    @POST
    @Logged
    @CrossOriginFilter
    @Path("activate/{username}")
    public Response activateUser(
            @PathParam("username") String aUsername,
            String aBody) {

        String activationCode = getActivationCode(aBody);

        ActivationTicket ticket = null;

        try {
            ticket = ActivationPool.create().getActivationTicket(activationCode);
        } catch (ActivationTicketNotFoundException e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }

        if(aUsername.equals(ticket.getUsername())) {
            AppUser appUser = userFacade.getUserByNameOrEmail(aUsername);
            appUser.setEmail(ticket.getEmail());
            appUser.activate();
            userFacade.updateUser(appUser);
        }

        Response response = Response.ok()
                .entity("{\"email\" : \""+ticket.getEmail()+"\"}")
                .build();

        return response;
    }

    private String getActivationCode(String aBody) {
        return aBody;
    }

}
