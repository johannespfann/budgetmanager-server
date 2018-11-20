package de.pfann.budgetmanager.server.restservices.resources.util;

public class UserValidator {

    public static void assertUsername(String aUserName) {
        if(aUserName == null || aUserName.isEmpty()){
            throw new IllegalArgumentException("Username is not valid");
        }
    }

}
