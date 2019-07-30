package de.pfann.budgetmanager.server.persistenscouchdb.file;

public class StartFileWriter {

    /*
server.adress: http://0.0.0.0
server.port: 8090
server.basepath: budget

# couchdb
couchdb.host: http://localhost
couchdb.port: 5984
couchdb.prefix: dev-bm

couchdb.pw:
couchdb.user:
     */

    // http://h2799032.stratoserver.net:81

    // http://pfann.org:5984

    /*
    public static void main(String[] args) throws MalformedURLException {
        // StdHttpClient.Builder httpClientBuilder = new StdHttpClient.Builder();
        // httpClientBuilder.url("http://pfann.org:5984");

        // HttpClient httpClient = httpClientBuilder.build();
        // HttpClient httpClient = httpClientBuilder.build();

        StdHttpClient.Builder httpClientBuilder = new StdHttpClient.Builder();
        httpClientBuilder.url("http://h2799032.stratoserver.net:81")
                .username("")
                .password("");

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClientBuilder.build());
        ObjectMapperFactory objectMapperFactory = new StdObjectMapperFactory();
        CouchDbConnectorFactory couchDbConnectorFactory = new CouchDbConnectorFactory(dbInstance,"bm",objectMapperFactory);

        CDBUserDaoFactory userDaoFactory = new CDBUserDaoFactory(couchDbConnectorFactory);
        CDBKontoDatabaseCreator kontoDatabaseFacade = new CDBKontoDatabaseCreator(couchDbConnectorFactory,dbInstance);
        CDBEntryDaoFactory entryDaoFactory = new CDBEntryDaoFactory(couchDbConnectorFactory);
        CDBStandingOrderDaoFactory standingOrderDaoFactory = new CDBStandingOrderDaoFactory(couchDbConnectorFactory);

        AppUserFacade userFacade = new CDBUserFacade(userDaoFactory, kontoDatabaseFacade);
        EntryFacade entryFacade = new CDBEntryFacade(userDaoFactory,entryDaoFactory);
        StandingOrderFacade standingOrderFacade = new CDBStandingOrderFacade(standingOrderDaoFactory,userDaoFactory);
        CDBStatisticFacade statisticFacade = new CDBStatisticFacade(userDaoFactory);

        JSONFileWriter writer = new JSONFileWriter(userFacade,entryFacade,standingOrderFacade, statisticFacade);

        List<AppUser> users = new LinkedList<>();
        users = userFacade.getAllUser();

        for(AppUser user : users) {
            writer.writeUserdataToFile(user.getName(), "C:\\Users\\jopf8\\OneDrive\\Desktop\\save_23_02_2019\\");
        }

    }
    */

}
