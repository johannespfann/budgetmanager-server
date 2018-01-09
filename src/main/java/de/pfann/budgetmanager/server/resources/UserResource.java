package de.pfann.budgetmanager.server.resources;

import de.pfann.budgetmanager.server.email.EmailService;
import de.pfann.budgetmanager.server.login.*;
import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.persistens.daos.AppUserDao;

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
    @Path("activate/{username}")
    public Response activateUser(
            @PathParam("username") String aUsername,
            String aBody) {

        String activationCode = getActivationCode(aBody);

        ActivationTicket ticket = null;

        try {
            ActivationPool.create().getActivationTicket(activationCode);

            if(aUsername.equals(ticket.getUsername())){
                AppUser appUser = userDao.getUser(aUsername);
                appUser.setEmail(ticket.getEmail());
                appUser.activate();
                userDao.save(appUser);
            }
            else{
                return RestUtil.prepareDefaultHeader(
                        Response.status(Response.Status.BAD_REQUEST))
                        .build();
            }

        } catch (ActivationTicketNotFoundException e) {
            e.printStackTrace();
            return RestUtil.prepareDefaultHeader(
                    Response.status(Response.Status.BAD_REQUEST))
                    .build();
        }

        return RestUtil.prepareDefaultHeader(Response.ok())
                .build();
    }

    private String getActivationCode(String aBody) {
        return aBody;
    }

}
