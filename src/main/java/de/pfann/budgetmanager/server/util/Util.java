package de.pfann.budgetmanager.server.util;

import java.util.Random;

public class Util {

    private static final String USERNAME_SEPERATOR = "#";

    public static String getUserNameWithUnique(String aName){
        return aName + USERNAME_SEPERATOR + getRandomInteger(1000,9999);
    }

    private static int getRandomInteger(int aMin, int aMax){
        Random rand = new Random();
        return rand.nextInt(aMax - aMin);
    }


}
