package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.common.configuration.ConfigurationProvider;
import de.pfann.budgetmanager.server.common.facade.*;
import de.pfann.budgetmanager.server.dataprovider.XMLTestDataManager;
import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBEntryDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBRunDoaFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBStandingOrderDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.*;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.ObjectMapperFactory;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;


public class StartDev
{

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        Application application = new Application();

        ConfigurationProvider configurationProvider = new ConfigurationProvider("budgetmanager.properties");
        Properties properties = configurationProvider.getProperties();
        Set<String> propertyList = properties.stringPropertyNames();

        for(String key : propertyList){
            System.out.println(key +" : " + properties.getProperty(key));
        }

        String couchdbadress = getCouchDBAdress(properties);

        StdHttpClient.Builder httpClientBuilder = new StdHttpClient.Builder();
        httpClientBuilder.url(couchdbadress);


        HttpClient httpClient = httpClientBuilder.build();
        cleanDb(httpClient);


        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        ObjectMapperFactory objectMapperFactory = new StdObjectMapperFactory();
        CouchDbConnectorFactory couchDbConnectorFactory = new CouchDbConnectorFactory(dbInstance,objectMapperFactory);

        CDBUserDaoFactory userDaoFactory = new CDBUserDaoFactory(couchDbConnectorFactory);
        CDBKontoDatabaseFacade kontoDatabaseFacade = new CDBKontoDatabaseFacade(couchDbConnectorFactory,dbInstance);
        CDBEntryDaoFactory entryDaoFactory = new CDBEntryDaoFactory(couchDbConnectorFactory);
        CDBStandingOrderDaoFactory standingOrderDaoFactory = new CDBStandingOrderDaoFactory(couchDbConnectorFactory);
        CDBRunDoaFactory runDaoFactory = new CDBRunDoaFactory(couchDbConnectorFactory);

        AppUserFacade userFacade = new CDBUserFacade(userDaoFactory, kontoDatabaseFacade);
        EntryFacade entryFacade = new CDBEntryFacade(userDaoFactory,entryDaoFactory);
        StandingOrderFacade standingOrderFacade = new CDBStandingOrderFacade(standingOrderDaoFactory,userDaoFactory);

        XMLTestDataManager testDataManager = new XMLTestDataManager(standingOrderFacade,entryFacade,userFacade);
        testDataManager.persistTestData("C:\\Users\\Johannes\\projects\\budgetmanager-server\\dataprovider\\src\\main\\resources\\");

        application.start(properties);
    }

    private static String getCouchDBAdress(Properties aProperties) {
        return new StringBuilder()
                .append(aProperties.getProperty(Application.KEY_COUCHDB_HOST))
                .append(":")
                .append(aProperties.getProperty(Application.KEY_COUCHDB_PORT))
                .toString();
    }

    private static void cleanDb(HttpClient httpClient) {
        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);

        List<String> dbs = dbInstance.getAllDatabases();

        for(String db : dbs){

            if(db.equals("_replicator")){
                System.out.println("was replicator");
                continue;
            }

            if(db.equals("_users")){
                System.out.println("was users");
                continue;
            }

            if(db.equals("_nodes")){
                System.out.println("was nodes");
                continue;
            }

            if(db.equals("_dbs")){
                System.out.println("was dbs");
                continue;
            }

            System.out.println("Deleting " + db);
            dbInstance.deleteDatabase(db);
        }
    }


}
