package de.pfann.budgetmanager.server.jobengine.core;

public class JobException extends Exception {

    public JobException(String aMessage){
        super(aMessage);
    }

    public JobException(Throwable aThrowable, String aMessage){
        super(aMessage,aThrowable);
    }

}