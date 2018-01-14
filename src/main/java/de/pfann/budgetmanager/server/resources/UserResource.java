package de.pfann.budgetmanager.server.resources;

import de.pfann.budgetmanager.server.email.EmailService;
import de.pfann.budgetmanager.server.login.*;
import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.persistens.daos.AppUserDao;
import de.pfann.budgetmanager.server.persistens.daos.NoUserFoundException;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    @Path("login/{accessor}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(
            @PathParam("accessor") String aAccessor,
            String body) {
        System.out.println("Login: " + aAccessor + "with" + getPassword(body));

        AppUser user = null;

        try {
            userDao.getUserByNameOrEmail(aAccessor);
        } catch (NoUserFoundException e) {
            // return error response
            return null;
        }

        if (!user.getPassword().equals(getPassword(body))) {
            // TODO error no access
            return null;
        }

        String accessToken = LoginUtil.getAccessTocken();

        AccessPool.getInstance().register(user, accessToken);

        Response response = RestUtil.prepareDefaultHeader(Response.ok())
                .entity("{\"accesstoken\" : \""+accessToken+"\"}")
                .build();
        return response;
    }

    @POST
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

        return RestUtil.prepareDefaultHeader(Response.ok())
                .entity("{\"username\" : \""+user.getName()+"\"}")
                .build();
    }

    private String getPassword(String aBody) {
        return aBody;
    }

    public Response unregister(@PathParam("username") String aUsername) {
        return null;
    }

    @POST
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

        return RestUtil.prepareDefaultHeader(Response.ok())
                .entity("{\"username\" : \""+aUsername+"\"}")
                .build();
    }

    @POST
    @Path("activate/{username}")
    public Response activateUser(
            @PathParam("username") String aUsername,
            String aBody) {
        System.out.println("Incomming activate user: " + aUsername);
        System.out.println("with Body:  " + aBody);

        String activationCode = getActivationCode(aBody);

        ActivationTicket ticket = null;

        try {
            ticket = ActivationPool.create().getActivationTicket(activationCode);
        } catch (ActivationTicketNotFoundException e) {
            e.printStackTrace();
            return RestUtil.prepareDefaultHeader(
                    Response.status(Response.Status.BAD_REQUEST))
                    .build();
        }

        System.out.println("Got Ticket => " + ticket.toString());
        // TODO fail with User compare username vs username#123123
        if(aUsername.equals(ticket.getUsername())){
            System.out.println("Persist User");
            AppUser appUser = null;
            try {
                appUser = userDao.getUserByName(aUsername);
            } catch (NoUserFoundException e) {
                // TODO Return errorresponse
                e.printStackTrace();
            }
            appUser.setEmail(ticket.getEmail());
            appUser.activate();
            userDao.save(appUser);
        }

        System.out.println("Prepare Response");
        Response response = RestUtil.prepareDefaultHeader(Response.ok())
                .entity("{\"email\" : \""+ticket.getEmail()+"\"}")
                .build();
        System.out.println("Versende response.ok");
        System.out.println(response.toString());
        return response;
    }

    private String getActivationCode(String aBody) {
        return aBody;
    }

}
