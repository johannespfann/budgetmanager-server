package de.pfann.budgetmanager.server.persistenscouchdb;

import de.pfann.budgetmanager.server.persistenscouchdb.core.BMObjectMapperFactory;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;

import java.net.MalformedURLException;

public class Main {

    public static void main(String[] args) throws MalformedURLException {
        System.out.println("Hello");

        HttpClient httpClient = new StdHttpClient.Builder()
                .url("http://localhost:5984")
                .build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        CouchDbConnector db = new StdCouchDbConnector("bm-johannes-1234-DLWNM", dbInstance, new BMObjectMapperFactory());

        db.createDatabaseIfNotExists();

    }
}
