package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.Tag;
import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBEntryDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    private static CDBEntryFacade createDaoFactory(){
        HttpClient httpClient = null;
        try {
            httpClient = new StdHttpClient.Builder()
                    .url("http://localhost:5984")
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        CouchDbConnectorFactory connectorFactory = new CouchDbConnectorFactory(dbInstance, new StdObjectMapperFactory());

        CDBUserDaoFactory userDaoFactory = new CDBUserDaoFactory(connectorFactory);
        CDBEntryDaoFactory entryDaoFactory = new CDBEntryDaoFactory(connectorFactory);
        CDBEntryFacade entryFacade = new CDBEntryFacade(userDaoFactory,entryDaoFactory);

        return entryFacade;

    }

    private static CDBUserFacade createUserFacade(){
        HttpClient httpClient = null;
        try {
            httpClient = new StdHttpClient.Builder()
                    .url("http://localhost:5984")
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        CouchDbConnectorFactory connectorFactory = new CouchDbConnectorFactory(dbInstance, new StdObjectMapperFactory());

        CDBUserDaoFactory userDaoFactory = new CDBUserDaoFactory(connectorFactory);
        CDBKontoDatabaseFacade kontoDatabaseFacade = new CDBKontoDatabaseFacade(connectorFactory, dbInstance);
        return new CDBUserFacade(userDaoFactory, kontoDatabaseFacade);
    }

    public static void main(String[] args) {
        AppUser user = new AppUser();
        user.setName("johannes");
        user.setEmail("johannes.pfann@gmx.net");
        user.setPassword("keymaster");
        user.setEncrypted(true);

        CDBUserFacade userFacade = createUserFacade();
        userFacade.createNewUser(user);

        Entry entry = new Entry();
        entry.setAppUser(user);
        entry.setAmount("123123");
        entry.setMemo("ein memo fuer mich");
        entry.setHash("1k13kj334");
        entry.setCreated_at(new Date());

        List<Tag> tagList = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setName("tag1");
        Tag tag2 = new Tag();
        tag2.setName("tag2");
        tagList.add(tag1);
        tagList.add(tag2);

        entry.setTags(tagList);

        CDBEntryFacade entryFacade = createDaoFactory();
        entryFacade.persistEntry(entry);
    }

}
