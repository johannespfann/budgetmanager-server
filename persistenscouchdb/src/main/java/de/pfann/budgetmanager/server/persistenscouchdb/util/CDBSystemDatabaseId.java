package de.pfann.budgetmanager.server.persistenscouchdb.util;

public class CDBSystemDatabaseId {

    private static final String PREFIX = "bm";

    private static final String SEPARATOR = "_";

    private CDBSystemDatabaseId(){
        // default
    }

    public static String createId(){
        return PREFIX + SEPARATOR + "application";
    }
}
