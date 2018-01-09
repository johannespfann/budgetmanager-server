package de.pfann.budgetmanager.server.login;

public class ActivationCodeAlreadyExistsException extends Exception {

    public ActivationCodeAlreadyExistsException(String aMsg) {
        super(aMsg);
    }

    public ActivationCodeAlreadyExistsException(Throwable aThrowable, String aMsg) {
        super(aMsg,aThrowable);
    }
}
