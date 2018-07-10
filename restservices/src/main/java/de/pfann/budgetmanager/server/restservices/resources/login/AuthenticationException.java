package de.pfann.budgetmanager.server.restservices.resources.login;

public class AuthenticationException extends Exception {

    public AuthenticationException(){
        super();
    }

    public AuthenticationException(String aMsg){
        super(aMsg);
    }

    public AuthenticationException(Throwable aThrowable, String aMsg){
        super(aMsg,aThrowable);
    }
}
