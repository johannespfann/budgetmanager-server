package de.pfann.budgetmanager.server.persistens.file;

import de.pfann.budgetmanager.server.persistens.util.CouchDBUtil;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;

import java.net.MalformedURLException;

public class CouchDbDeleter {

    public static void main(String[] args) throws MalformedURLException {
        StdHttpClient.Builder httpClientBuilder = new StdHttpClient.Builder();
        httpClientBuilder
                .url("http://localhost:5984");
        //.url("http://pfann.org:5984")
        //.username("admin")
        //.password("5kassandra5");
        HttpClient httpClient = httpClientBuilder.build();


        CouchDBUtil couchDBUtil = new CouchDBUtil(httpClient);
        couchDBUtil.deleteDatabases("bm");
    }
}
