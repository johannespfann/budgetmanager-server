package de.pfann.budgetmanager.server.persistenscouchdb.file;

public class StartCouchDbWriter {

    /**
     StdHttpClient.Builder httpClientBuilder = new StdHttpClient.Builder();
     httpClientBuilder
     .url("http://h2799032.stratoserver.net/couchdb")
     //.url("http://pfann.org:5984")
     .username("")
     .password("");
     HttpClient httpClient = httpClientBuilder.build();
     */

    /**
     StdHttpClient.Builder httpClientBuilder = new StdHttpClient.Builder();
     httpClientBuilder.url("http://localhost:5984");
     HttpClient httpClient = httpClientBuilder.build();
     */

    /*
    public static void main(String[] args) throws MalformedURLException {
        StdHttpClient.Builder httpClientBuilder = new StdHttpClient.Builder();
        httpClientBuilder
                .url("http://localhost:5984");
                //.url("http://pfann.org:5984")
                //.username("")
                //.password("");
        HttpClient httpClient = httpClientBuilder.build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        ObjectMapperFactory objectMapperFactory = new StdObjectMapperFactory();
        CouchDbConnectorFactory couchDbConnectorFactory = new CouchDbConnectorFactory(dbInstance,"bm",objectMapperFactory);
        CouchDBUtil couchDBUtil = new CouchDBUtil(httpClient);

        couchDBUtil.deleteDatabases("bm");

        CDBUserDaoFactory userDaoFactory = new CDBUserDaoFactory(couchDbConnectorFactory);
        CDBKontoDatabaseCreator kontoDatabaseFacade = new CDBKontoDatabaseCreator(couchDbConnectorFactory,dbInstance);
        CDBEntryDaoFactory entryDaoFactory = new CDBEntryDaoFactory(couchDbConnectorFactory);
        CDBStandingOrderDaoFactory standingOrderDaoFactory = new CDBStandingOrderDaoFactory(couchDbConnectorFactory);

        CDBUserFacade userFacade = new CDBUserFacade(userDaoFactory, kontoDatabaseFacade);
        EntryFacade entryFacade = new CDBEntryFacade(userDaoFactory,entryDaoFactory);
        StandingOrderFacade standingOrderFacade = new CDBStandingOrderFacade(standingOrderDaoFactory,userDaoFactory);
        CDBStatisticFacade statisticFacade = new CDBStatisticFacade(userDaoFactory);

        JSONCouchDBWriter couchdbWriter = new JSONCouchDBWriter(userFacade, entryFacade, standingOrderFacade, statisticFacade);


        // File directory = new File("C:\\Users\\Johannes\\Desktop\\output"  + "\\johannes-1234");
        File inputDirectory = new File("C:\\Users\\jopf8\\OneDrive\\Desktop\\save_23_02_2019\\");

        File[] childs = inputDirectory.listFiles();
        List<String> users = new LinkedList<>();
        for(File childFile : childs){
            users.add(childFile.getName());
        }


        System.out.println("Users: ");
        for(String userName : users){
            //File userDirectory = new File(inputDirectory.)
            System.out.println("Prepare user: " + userName);
            File userDirectory = new File(inputDirectory.getAbsolutePath() + "\\" + userName);
            couchdbWriter.writeUserdataToCouchDb(userDirectory);
        }


    }
    */

}
