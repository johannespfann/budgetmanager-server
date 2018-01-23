package de.pfann.budgetmanager.server.resources;

import de.pfann.budgetmanager.server.email.EmailService;
import de.pfann.budgetmanager.server.login.*;
import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.persistens.daos.AppUserDao;
import de.pfann.budgetmanager.server.persistens.daos.NoUserFoundException;
import de.pfann.budgetmanager.server.resources.core.Logged;
import de.pfann.budgetmanager.server.resources.core.ModifyCrossOrigin;
import de.pfann.budgetmanager.server.resources.core.Secured;
import de.pfann.budgetmanager.server.util.LogUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user/")
public class UserResource implements UserApi {

    private AppUserDao userDao;
    private EmailService emailService;

    public UserResource(){
        userDao = AppUserDao.create();
        emailService = new EmailService();
    }


    @POST
    @ModifyCrossOrigin
    @Secured
    @Logged
    @Path("logout/{accessor}")
    public Response logout(
            @PathParam("accessor") String aAccessor
            ) {

        String accessCode = "asdf"; //getAccessCode(aBody);

        AppUser user = null;
        try {
            user = userDao.getUserByNameOrEmail(aAccessor);
        } catch (NoUserFoundException e) {
            return Response.serverError()
                    .build();
        }

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
    @ModifyCrossOrigin
    @Path("login/{accessor}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(
            @PathParam("accessor") String aAccessor,
            String body) {

        AppUser user;

        try {
            user = userDao.getUserByNameOrEmail(aAccessor);
        } catch (NoUserFoundException e) {
            return Response.status(403)
                    .build();
        }

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
    @ModifyCrossOrigin
    @Path("register/{username}/email/{email}")
    public Response register(
            @PathParam("username") String aUsername,
            @PathParam("email") String aEmail,
            String aBody) {

        AppUser user = new AppUser();
        user.setName(LoginUtil.getUserNameWithUnique(aUsername));
        user.setEmail(aEmail);
        user.setPassword(getPassword(aBody));

        userDao.save(user);

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
    @ModifyCrossOrigin
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
    @ModifyCrossOrigin
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

        if(aUsername.equals(ticket.getUsername())){
            AppUser appUser = null;
            try {
                appUser = userDao.getUserByName(aUsername);
            } catch (NoUserFoundException e) {
                e.printStackTrace();
            }

            appUser.setEmail(ticket.getEmail());
            appUser.activate();
            userDao.save(appUser);
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
