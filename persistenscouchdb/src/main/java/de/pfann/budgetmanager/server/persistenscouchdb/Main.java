package de.pfann.budgetmanager.server.persistenscouchdb;

import de.pfann.budgetmanager.server.persistenscouchdb.core.BMObjectMapperFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.EntryDao;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBEntry;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;

import java.net.MalformedURLException;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws MalformedURLException {
        System.out.println("Hello");

        HttpClient httpClient = new StdHttpClient.Builder()
                .url("http://localhost:5984")
                .build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        CouchDbConnector db = new StdCouchDbConnector("bm-johannes-1234-DLWNM", dbInstance, new BMObjectMapperFactory());

        EntryDao entryDao = new EntryDao(db);

        CDBEntry entry = new CDBEntry();

        entry.setAmount("123123");
        entry.setCreated_at(LocalDateTime.now());
        entry.setHash("asdfasdf");
        entry.setKonto("DLWMM");
        entry.setMemo("meom");
        entry.setId(generateEntryId("johannes-1234","DLWMM",LocalDateTime.now()));

        entryDao.add(entry);
    }

    private static String generateEntryId(String aUsername, String aKonto, LocalDateTime aCreatedAt){
        return new StringBuilder("entry").append(":")
                .append(aUsername).append(":")
                .append(aKonto).append(":")
                .append(aCreatedAt.getYear()).append(":")
                .append(aCreatedAt.getMonth().getValue())
                .toString();
    }
}
