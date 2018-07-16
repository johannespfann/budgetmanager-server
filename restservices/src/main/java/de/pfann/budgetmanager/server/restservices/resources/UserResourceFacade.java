package de.pfann.budgetmanager.server.restservices.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.common.email.EmailService;
import de.pfann.budgetmanager.server.restservices.resources.email.ActivationEmailGenerator;
import de.pfann.budgetmanager.server.restservices.resources.login.*;

public class UserResourceFacade {

    private ObjectMapper objectMapper;
    private AppUserFacade userFacade;
    private EmailService emailService;
    private AuthenticationManager authenticationManager;
    private ActivationPool activationPool;


    public UserResourceFacade(AppUserFacade aAppUserFacade, EmailService aEmailService, AuthenticationManager aAuthManager, ActivationPool aActivationPool){
        userFacade = aAppUserFacade;
        emailService = aEmailService;
        authenticationManager = aAuthManager;
        objectMapper = new ObjectMapper();
        activationPool = aActivationPool;
    }

    public void logout(String aUser, String aToken){
        try{

            AppUser user = userFacade.getUserByNameOrEmail(aUser);
            AccessPool.getInstance().unregister(user, aToken);

        }catch (Exception exception){
            exception.printStackTrace();
            throw exception;
        }
    }

    public String login(String aUser, String aAuthorizationValue) throws JsonProcessingException {
        try{
            LogUtil.info(this.getClass(),"Authorization: " + aAuthorizationValue);
            AppUser user = userFacade.getUserByNameOrEmail(aUser);
            LogUtil.info(this.getClass(),"Found user: " + user.getName());

            LogUtil.info(this.getClass(),"user    : " + LoginUtil.extractUser(aAuthorizationValue));
            LogUtil.info(this.getClass(),"password: " + LoginUtil.extractPassword(aAuthorizationValue));

            String username = LoginUtil.extractUser(aAuthorizationValue);
            String password = LoginUtil.extractPassword(aAuthorizationValue);

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

            String token = authenticationManager.generateToken(user.getName());

            System.out.println("preparedValue: " + token);
            String JSON = "{\"accesstoken\" : \"" + token +"\"," +
                    "\"username\" : \"" + user.getName() +"\"," +
                    "\"email\" : \"" + user.getEmail() +"\" }";
            return JSON;
        }catch (Exception exception){
            exception.printStackTrace();
            throw exception;
        }
    }

    public String register(String aUsername, String aEmail, String Password) {
        AppUser user = new AppUser();
        user.setName(LoginUtil.getUserNameWithUnique(aUsername));
        user.setEmail(aEmail);
        user.setPassword(Password);

        try{
            userFacade.createNewUser(user);

            String activationCode = LoginUtil.generateActivationCode();

            try {
                activationPool.addActivationTicket(user.getName(),aEmail,activationCode);
                ActivationEmailGenerator generator = new ActivationEmailGenerator(user.getName(),aEmail,activationCode);
                emailService.sendEmail(aEmail,generator.getSubject(),generator.getContent());

            } catch (ActivationCodeAlreadyExistsException e) {
                e.printStackTrace();
            }
        }catch (Exception exception){
            exception.printStackTrace();
            throw exception;
        }
        return user.getName();
    }

    public void resendEmail(String aUsername, String aEmail){
        try {
            String activationCode = LoginUtil.generateActivationCode();
            activationPool.addActivationTicket(aUsername, aEmail, activationCode);
            ActivationEmailGenerator generator = new ActivationEmailGenerator(aUsername,aEmail,activationCode);
            emailService.sendEmail(aEmail,generator.getSubject(),generator.getSubject());
        }catch (ActivationCodeAlreadyExistsException activCodeException){
            activCodeException.printStackTrace();
        }catch (Exception exception){
            exception.printStackTrace();
            throw exception;
        }
    }

    public void activeUser(String aUsername, String aActivationCode){
        try{
            if(!activationPool.codeExists(aActivationCode)) {
                throw new IllegalArgumentException("Code does not exists!");
            }
            ActivationTicket ticket = activationPool.getActivationTicket(aActivationCode);

            if(aUsername.equals(ticket.getUsername())) {
                AppUser appUser = userFacade.getUserByNameOrEmail(aUsername);
                appUser.setEmail(ticket.getEmail());
                appUser.activate();
                userFacade.updateUser(appUser);
            }
        }catch (Exception exception){
            exception.printStackTrace();
            throw exception;
        }
    }


}
