package de.pfann.budgetmanager.server.core.util;

import java.util.Random;

public class Util {

    public static String getUniueHash(int aMin, int aMax){
        Random rand = new Random();
        return String.valueOf(rand.nextInt(aMax - aMin));
    }


}
