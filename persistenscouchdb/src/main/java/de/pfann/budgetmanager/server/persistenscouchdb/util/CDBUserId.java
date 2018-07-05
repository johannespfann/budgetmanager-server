package de.pfann.budgetmanager.server.persistenscouchdb.util;

public class CDBUserId {

    public static final String SEPERATOR = ":";
    public static final String TYP_PREFIX = "user";

    private String username;

    public static CDBUserId create(String aUsername){
        assertUserIsValid(aUsername);
        return new CDBUserId(aUsername);
    }

    public static CDBUserId createWithString(String aValue){
        String[] values = aValue.split(SEPERATOR);
        assertPrefixIsValid(values[0]);
        return new CDBUserId(values[1]);
    }

    private CDBUserId(String aUsername){
        username = aUsername;
    }


    public String getUsername() {
        return username;
    }

    public String toString(){
        return TYP_PREFIX + SEPERATOR + username;
    }

    private static void assertPrefixIsValid(String value) {
        if(value == null || !value.equals(TYP_PREFIX)){
            throw new IllegalArgumentException();
        }
    }

    private static void assertUserIsValid(String aUsername) {
        if(aUsername == null || aUsername.isEmpty()){
            throw new IllegalArgumentException();
        }
    }
}
