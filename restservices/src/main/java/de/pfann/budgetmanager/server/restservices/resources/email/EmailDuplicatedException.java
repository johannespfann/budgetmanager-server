package de.pfann.budgetmanager.server.restservices.resources.email;

public class EmailDuplicatedException extends RuntimeException {

    public EmailDuplicatedException() {
        super();
    }

    public EmailDuplicatedException(String aMessage) {
        super(aMessage);
    }

    public EmailDuplicatedException(String aMessage, Throwable aThrowable) {
        super(aMessage,aThrowable);
    }

}
