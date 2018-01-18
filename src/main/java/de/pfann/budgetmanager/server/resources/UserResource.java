package de.pfann.budgetmanager.server.resources;

import de.pfann.budgetmanager.server.email.EmailService;
import de.pfann.budgetmanager.server.login.*;
import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.persistens.daos.AppUserDao;
import de.pfann.budgetmanager.server.persistens.daos.NoUserFoundException;
import de.pfann.budgetmanager.server.resources.core.Logged;
import de.pfann.budgetmanager.server.resources.core.ModifyCrossOrigin;


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

    @GET
    @Logged
    @ModifyCrossOrigin
    @Path("hello")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sayhello(){
        Response response = Response.ok()
                .entity("{\"accesstoken\" : \"" +"johannes123"+"\"," +
                        "\"username\" : \" johannes \"," +
                        "\"email\" : \" email \" }")
                .build();
        return response;
    }

    @POST
    @Logged
    @ModifyCrossOrigin
    @Path("login/{accessor}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(
            @PathParam("accessor") String aAccessor,
            String body) {
        AppUser user = null;


        try {
            System.out.println("Login: Try to get User");
            user = userDao.getUserByNameOrEmail(aAccessor);
            System.out.println("Login: User: " + user.getName());
        } catch (NoUserFoundException e) {
            // return error response
            return null;
        }

        if(user == null){
            System.out.println("Login: user was null");
            return null;
        }

        System.out.println("Login: compare pw: " + user.getPassword() + " and " + getPassword(body));
        if (!user.getPassword().equals(getPassword(body))) {
            System.out.println("Login: user pw was wrong");
            return null;
        }


        String accessToken = LoginUtil.getAccessTocken();
        System.out.println("Login: access: " + accessToken);
        AccessPool.getInstance().register(user, accessToken);

        System.out.println("Login: registered");

        Response response = Response.ok()
                .entity("{\"accesstoken\" : \"" +accessToken+"\"," +
                        "\"username\" : \"" +user.getName()+"\"," +
                        "\"email\" : \"" +user.getEmail()+"\" }")
                .build();
        System.out.println("return response: " + response.getEntity().toString());
        return response;
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

    public Response unregister(@PathParam("username") String aUsername) {
        return null;
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
        Response response = Response.ok()
                .entity("{\"email\" : \""+ticket.getEmail()+"\"}")
                .build();
        return response;
    }

    private String getActivationCode(String aBody) {
        return aBody;
    }

}
