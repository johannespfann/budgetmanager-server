package de.pfann.budgetmanager.server.login;

import java.util.Random;

public class Util {

    /**
     * Create a random integer
     * @return integer as string
     */
    public static String getActivationCode(){
        return String.valueOf(new Random().nextInt(99999 - 10000));
    }
}
