package de.pfann.budgetmanager.server.persistenscouchdb.file;

public class CouchDBTest {

    private static final String DB_NAME_PREFIX = "test";
/*
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
        CDBKontoDatabaseCreator kontoDatabaseFacade = new CDBKontoDatabaseCreator(couchDbConnectorFactory,dbInstance);
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
    */
}
