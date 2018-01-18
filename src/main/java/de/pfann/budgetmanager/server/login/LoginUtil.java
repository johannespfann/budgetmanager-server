package de.pfann.budgetmanager.server.login;

import java.util.Random;

public class LoginUtil {

    private static final String USERNAME_SEPERATOR = "-";

    /**
     * Create a random integer
     * @return integer as string
     */
    public static String getActivationCode(){
        return String.valueOf(new Random().nextInt(99999 - 10000));
    }

    /**
     * Create a unique username with a random integer
     * {username}#{random_integer}
     * @param aUsername
     * @return
     */
    public static String getUserNameWithUnique(String aUsername){
        return aUsername + USERNAME_SEPERATOR + getRandomInteger(1000,9999);
    }

    private static int getRandomInteger(int aMin, int aMax){
        Random rand = new Random();
        return rand.nextInt(aMax - aMin);
    }

    /**
     *
     * @return a unique access token
     */
    public static String getAccessTocken(){
        return String.valueOf(getRandomInteger(1000000,99999999));
    }

}
