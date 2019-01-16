package de.pfann.budgetmanager.server.restservices.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.pfann.budgetmanager.server.common.email.EmailService;
import de.pfann.budgetmanager.server.common.facade.UserFacade;
import de.pfann.budgetmanager.server.common.util.LoginUtil;
import de.pfann.budgetmanager.server.model.User;
import de.pfann.budgetmanager.server.restservices.resources.email.ActivationEmailGenerator;
import de.pfann.budgetmanager.server.restservices.resources.email.EmailDuplicatedException;
import de.pfann.budgetmanager.server.restservices.resources.login.ActivationCodeAlreadyExistsException;
import de.pfann.budgetmanager.server.restservices.resources.login.ActivationPool;
import de.pfann.budgetmanager.server.restservices.resources.login.ActivationTicket;
import de.pfann.budgetmanager.server.restservices.resources.login.AuthenticationManager;
import de.pfann.server.logging.core.LogUtil;

import java.util.LinkedList;
import java.util.List;

public class V2UserResourceFacade {

    private UserFacade userFacade;
    private EmailService emailService;
    private AuthenticationManager authenticationManager;
    private ActivationPool activationPool;


    public V2UserResourceFacade(UserFacade aUserFacade, EmailService aEmailService, AuthenticationManager aAuthManager, ActivationPool aActivationPool){
        userFacade = aUserFacade;
        emailService = aEmailService;
        authenticationManager = aAuthManager;
        activationPool = aActivationPool;
    }

    public void logout(String aUser, String aToken){
        try{

            User user = userFacade.getUserByNameOrEmail(aUser);
            //AccessPool.getInstance().unregister(user, aToken);

        }catch (Exception exception){
            exception.printStackTrace();
            throw exception;
        }
    }

    public String login(String aIdentifier, String aAuthorizationValue) throws JsonProcessingException {
        try{
            LogUtil.info(this.getClass(),"Authorization: " + aAuthorizationValue);
            User user = userFacade.getUserByNameOrEmail(aIdentifier);
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

            if(!isValidUser(aIdentifier,user)){
                LogUtil.info(this.getClass(),"user was not the same: " + user.getName() + " and " + username);
                throw new SecurityException();
            }

            if(!user.getPassword().equals(password)){
                LogUtil.info(this.getClass(),"password was wrong: " + user.getPassword() + " and " + password);
                throw new SecurityException();
            }

            String token = authenticationManager.generateToken(user.getName());

            String JSON = "{\"accesstoken\" : \"" + token +"\"," +
                    "\"username\" : \"" + user.getName() +"\"," +
                    "\"email\" : \"" + user.getEmails().get(0) +"\" }";
            return JSON;
        }catch (Exception exception){
            exception.printStackTrace();
            throw exception;
        }
    }

    private boolean isValidUser(String aIdentifier, User aUser) {
        boolean isValid = false;

        if(aIdentifier.equals(aUser.getName())){
            isValid = true;
        }

        for(String email : aUser.getEmails()) {
            if(email.equals(aIdentifier)){
                isValid = true;
            }
        }

        return isValid;
    }

    public String register(String aUsername, String aEmail, String Password) {
        System.out.println("register");
        String validUsername = aUsername.trim().toLowerCase();

        List<String> emails = new LinkedList<>();

        validateEmail(aEmail);
        emails.add(aEmail);

        User user = new User();
        user.setName(LoginUtil.getUserNameWithUnique(validUsername));
        user.setEmails(emails);
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
        System.out.println("resend email");

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
        System.out.println("activateUser");
        try{
            if(!activationPool.codeExists(aActivationCode)) {
                throw new IllegalArgumentException("Code does not exists!");
            }
            ActivationTicket ticket = activationPool.getActivationTicket(aActivationCode);

            if(aUsername.equals(ticket.getUsername())) {
                User user = userFacade.getUserByNameOrEmail(aUsername);
                System.out.println("got user by name");
                List<String> emails = new LinkedList<>();
                emails.add(ticket.getEmail());

                user.setEmails(emails);
                user.activate();
                userFacade.updateUser(user);
            }
        }catch (Exception exception){
            exception.printStackTrace();
            throw exception;
        }
    }

    public User getUserInfomation(final String aUsername){
        User user  = userFacade.getUserByNameOrEmail(aUsername);
        user.setPassword("");
        return user;
    }


    private void validateEmail(String aEmail) {
        if(userFacade.isEmailAlreadyExists(aEmail)) {
            throw new EmailDuplicatedException("Email " + aEmail + " already exists!");
        }
    }


}
