package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.common.configuration.ConfigurationProvider;
import de.pfann.budgetmanager.server.common.facade.*;
import de.pfann.budgetmanager.server.common.model.Run;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import java.util.Set;


public class StartDev
{

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        Application application = new Application();

        ConfigurationProvider configurationProvider = new ConfigurationProvider("dev-budgetmanager.properties");
        Properties properties = configurationProvider.getProperties();
        Set<String> propertyList = properties.stringPropertyNames();

        for(String key : propertyList){
            System.out.println(key +" : " + properties.getProperty(key));
        }

        String couchdbadress = getCouchDBAdress(properties);
        String couchDbPrefix = properties.getProperty(Application.KEY_COUCHDB_PREFIX);

        StdHttpClient.Builder httpClientBuilder = new StdHttpClient.Builder();
        httpClientBuilder.url(couchdbadress);


        HttpClient httpClient = httpClientBuilder.build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        ObjectMapperFactory objectMapperFactory = new StdObjectMapperFactory();
        CouchDbConnectorFactory couchDbConnectorFactory = new CouchDbConnectorFactory(dbInstance,couchDbPrefix,objectMapperFactory);

        CDBUserDaoFactory userDaoFactory = new CDBUserDaoFactory(couchDbConnectorFactory);
        CDBKontoDatabaseFacade kontoDatabaseFacade = new CDBKontoDatabaseFacade(couchDbConnectorFactory,dbInstance);
        CDBEntryDaoFactory entryDaoFactory = new CDBEntryDaoFactory(couchDbConnectorFactory);
        CDBStandingOrderDaoFactory standingOrderDaoFactory = new CDBStandingOrderDaoFactory(couchDbConnectorFactory);
        CDBRunDoaFactory runDaoFactory = new CDBRunDoaFactory(couchDbConnectorFactory);

        AppUserFacade userFacade = new CDBUserFacade(userDaoFactory, kontoDatabaseFacade);
        EntryFacade entryFacade = new CDBEntryFacade(userDaoFactory,entryDaoFactory);
        StandingOrderFacade standingOrderFacade = new CDBStandingOrderFacade(standingOrderDaoFactory,userDaoFactory);

        XMLTestDataManager testDataManager = new XMLTestDataManager(standingOrderFacade,entryFacade,userFacade);
        testDataManager.persistTestData("C:\\Users\\jopf8\\projects\\budgetmanager-server\\dataprovider\\src\\main\\resources");

        Run run = new Run(LocalDateTime.of(2018,06,01,3,0,0));
        RunFacade runFacade = new CDBRunFacade(runDaoFactory);
        runFacade.persist(run);

        application.start(properties);
    }

    private static String getCouchDBAdress(Properties aProperties) {
        return new StringBuilder()
                .append(aProperties.getProperty(Application.KEY_COUCHDB_HOST))
                .append(":")
                .append(aProperties.getProperty(Application.KEY_COUCHDB_PORT))
                .toString();
    }




}
