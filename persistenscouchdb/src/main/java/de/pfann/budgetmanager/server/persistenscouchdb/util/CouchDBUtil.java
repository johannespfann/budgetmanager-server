package de.pfann.budgetmanager.server.persistenscouchdb.util;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import java.util.LinkedList;
import java.util.List;

public class CouchDBUtil {

    private CouchDbInstance couchDbInstance;

    public CouchDBUtil(HttpClient aHttpClient) {
        couchDbInstance = new StdCouchDbInstance(aHttpClient);
    }

    public List<String> getDatabases(String aPrefix){
        List<String> databaseNames = getDatabases();
        List<String> filteredDbNames = new LinkedList<>();
        for (String db : databaseNames) {
            String[] parts = db.split("_");
            String prefix = parts[0];
            if (prefix.equals(aPrefix)) {
                filteredDbNames.add(db);
            }
        }
        return filteredDbNames;
    }

    public void printDatabases(String aPrefix){
        for(String dbNames : getDatabases(aPrefix)){
            System.out.println(dbNames);
        }
    }

    public void printDatabases() {
        for (String dbName : getDatabases()) {
            System.out.println(dbName);
        }
    }

    public List<String> getDatabases() {
        List<String> databaseNames = couchDbInstance.getAllDatabases();
        List<String> filteredDatabaseNames = filterDatabaseNames(databaseNames);
        return filteredDatabaseNames;
    }

    public void deleteDatabases(String aPrefix) {
        List<String> databaseNames = getDatabases(aPrefix);

        for(String name : databaseNames) {
            System.out.println("Delete: " + name);
            couchDbInstance.deleteDatabase(name);
        }
    }

    private List<String> filterDatabaseNames(List<String> aDatabaseNames) {
        List<String> filteredDatabaseNames = new LinkedList<>();
        for (String dbNames : aDatabaseNames) {
            if (dbNames.equals("_dbs")) {
                continue;
            }
            if (dbNames.equals("_nodes")) {
                continue;
            }
            if (dbNames.equals("_replicator")) {
                continue;
            }
            if (dbNames.equals("_users")) {
                continue;
            }
            filteredDatabaseNames.add(dbNames);
        }
        return filteredDatabaseNames;
    }
}
