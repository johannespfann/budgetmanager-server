package de.pfann.budgetmanager.server.core.persistens.daos;

public class NoUserFoundException extends Exception {

    public NoUserFoundException(Throwable aThrowable, String aMessage){
        super(aMessage,aThrowable);
    }

    public NoUserFoundException(String aMessage){
        super(aMessage);
    }

}
