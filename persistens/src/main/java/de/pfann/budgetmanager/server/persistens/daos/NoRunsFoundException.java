package de.pfann.budgetmanager.server.persistens.daos;

public class NoRunsFoundException extends Exception {

    public NoRunsFoundException(String aMessage){
        super(aMessage);
    }

    public NoRunsFoundException(String aMessage, Throwable aThrowable){
        super(aMessage,aThrowable);
    }
}
