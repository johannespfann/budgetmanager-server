package de.pfann.budgetmanager.server.core.resources;

import de.pfann.budgetmanager.server.core.email.EmailService;
import de.pfann.budgetmanager.server.core.login.*;
import de.pfann.budgetmanager.server.core.model.AppUser;
import de.pfann.budgetmanager.server.core.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.core.resources.core.Logged;
import de.pfann.budgetmanager.server.core.resources.core.AllowCrossOrigin;
import de.pfann.budgetmanager.server.core.resources.core.Secured;
import de.pfann.budgetmanager.server.core.util.LogUtil;

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
    @AllowCrossOrigin
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

    @POST
    @Logged
    @AllowCrossOrigin
    @Path("login/{accessor}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(
            @PathParam("accessor") String aAccessor,
            String body) {

        AppUser user = userFacade.getUserByNameOrEmail(aAccessor);

        if (!user.getPassword().equals(getPassword(body))) {
            return Response.status(403)
                    .build();
        }

        String accessToken = LoginUtil.getAccessTocken();
        AccessPool.getInstance().register(user, accessToken);

        return Response.ok()
                .entity("{\"accesstoken\" : \"" + accessToken +"\"," +
                        "\"username\" : \"" + user.getName() +"\"," +
                        "\"email\" : \"" + user.getEmail() +"\" }")
                .build();
    }

    @POST
    @Logged
    @AllowCrossOrigin
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
    @AllowCrossOrigin
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
    @AllowCrossOrigin
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
