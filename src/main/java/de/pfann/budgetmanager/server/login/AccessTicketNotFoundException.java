package de.pfann.budgetmanager.server.login;

public class AccessTicketNotFoundException extends Exception {

    public AccessTicketNotFoundException(String aMsg) {
        super(aMsg);
    }

    public AccessTicketNotFoundException(Throwable aThrowable, String aMsg) {
        super(aMsg,aThrowable);
    }
}
