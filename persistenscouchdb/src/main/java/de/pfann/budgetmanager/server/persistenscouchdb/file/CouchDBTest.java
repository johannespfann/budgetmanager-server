package de.pfann.budgetmanager.server.persistenscouchdb.file;

import de.pfann.budgetmanager.server.common.facade.EntryFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.Tag;
import de.pfann.budgetmanager.server.common.util.LoginUtil;
import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBEntryDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.CDBEntryFacade;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.CDBKontoDatabaseFacade;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.CDBUserFacade;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CouchDBUtil;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.ObjectMapperFactory;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CouchDBTest {

    private static final String DB_NAME_PREFIX = "test";

    public static void main(String[] args) throws MalformedURLException {

        StdHttpClient.Builder httpClientBuilder = new StdHttpClient.Builder();
        httpClientBuilder
                .url("http://localhost:5984");
        //.url("http://pfann.org:5984")
        //.username("admin")
        //.password("5kassandra5");
        HttpClient httpClient = httpClientBuilder.build();
        CouchDBUtil util = new CouchDBUtil(httpClient);
        util.getDatabases(DB_NAME_PREFIX);

        CouchDBUtil couchDBUtil = new CouchDBUtil(httpClient);
        couchDBUtil.printDatabases();
        couchDBUtil.printDatabases(DB_NAME_PREFIX);

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        ObjectMapperFactory objectMapperFactory = new StdObjectMapperFactory();
        CouchDbConnectorFactory couchDbConnectorFactory = new CouchDbConnectorFactory(dbInstance, DB_NAME_PREFIX,objectMapperFactory);

        CDBUserDaoFactory userDaoFactory = new CDBUserDaoFactory(couchDbConnectorFactory);
        CDBKontoDatabaseFacade kontoDatabaseFacade = new CDBKontoDatabaseFacade(couchDbConnectorFactory,dbInstance);
        CDBEntryDaoFactory entryDaoFactory = new CDBEntryDaoFactory(couchDbConnectorFactory);

        CDBUserFacade userFacade = new CDBUserFacade(userDaoFactory, kontoDatabaseFacade);
        EntryFacade entryFacade = new CDBEntryFacade(userDaoFactory,entryDaoFactory);


        AppUser appUser = createAppUser();

        userFacade.createNewUser(appUser);
        userFacade.activateUser(appUser);

        Entry entry = createEntry(appUser);

        entryFacade.persistEntry(entry);



        AppUser peterUser = userFacade.getUserByNameOrEmail("peter-8780");

        List<Entry> entries = entryFacade.getEntries(peterUser);

        AppUser tempUser = new AppUser();
        tempUser.setName("peter-8780");

        for(Entry tempEntry : entries) {
            System.out.println(tempEntry.getHash());
            System.out.println(tempEntry.getAmount());
            tempEntry.setAmount("8888");
            tempEntry.setAppUser(tempUser);

            entryFacade.update(tempEntry);
        }

    }

    private static Entry createEntry(AppUser appUser) {
        Entry entry = new Entry();
        entry.setAmount("123");
        entry.setAppUser(appUser);
        entry.setHash("2345g3wfg3q34");
        entry.setCreated_at(new Date());
        entry.setMemo("text");
        entry.addTag(new Tag("luxus"));
        entry.addTag(new Tag("super"));
        return entry;
    }

    private static AppUser createAppUser() {
        AppUser appUser = new AppUser();
        appUser.setName(LoginUtil.getUserNameWithUnique("peter"));
        appUser.setEmail("peter@muster.de");
        appUser.setEncrypted(true);
        appUser.setEncryptionText("neuer bester Text");
        return appUser;
    }
}
