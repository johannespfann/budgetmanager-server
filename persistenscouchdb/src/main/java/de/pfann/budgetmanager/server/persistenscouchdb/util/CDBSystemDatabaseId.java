package de.pfann.budgetmanager.server.persistenscouchdb.util;

public class CDBSystemDatabaseId {

    private static final String APPLICATION_DATABASE_NAME = "application";

    private CDBSystemDatabaseId(){
        // default
    }

    public static String createId(){
        return APPLICATION_DATABASE_NAME;
    }
}
