package de.pfann.budgetmanager.server.restservices.resources.util;

public class EmailValidator {

    public static void assertEmailIsValid(String aEmail) {
        if(aEmail == null || aEmail.isEmpty() || !emailIsValid(aEmail)){
            throw new IllegalArgumentException("Falsches Argument fuer Email: " + aEmail);
        }
    }

    private static boolean emailIsValid(String aEmail) {
        // TODO
        return true;
    }

}
