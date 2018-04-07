package de.pfann.budgetmanager.server.core.login;

public class ActivationTicketNotFoundException extends Exception {

    ActivationTicketNotFoundException(String aMsg){
        super(aMsg);
    }

    ActivationTicketNotFoundException(Throwable aThrowable, String aMsg){
        super(aMsg,aThrowable);
    }
}
