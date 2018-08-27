package de.pfann.budgetmanager.server.persistenscouchdb.file;

import de.pfann.budgetmanager.server.common.facade.EntryFacade;
import de.pfann.budgetmanager.server.common.facade.StandingOrderFacade;
import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBEntryDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBStandingOrderDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.*;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CouchDBUtil;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.ObjectMapperFactory;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

public class StartCouchDbWriter {


    public static void main(String[] args) throws MalformedURLException {
        StdHttpClient.Builder httpClientBuilder = new StdHttpClient.Builder();
        httpClientBuilder.url("http://localhost:5984");

        HttpClient httpClient = httpClientBuilder.build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        ObjectMapperFactory objectMapperFactory = new StdObjectMapperFactory();
        CouchDbConnectorFactory couchDbConnectorFactory = new CouchDbConnectorFactory(dbInstance,"copy-bm",objectMapperFactory);
        CouchDBUtil couchDBUtil = new CouchDBUtil(httpClient);

        couchDBUtil.deleteDatabases("copy-bm");

        CDBUserDaoFactory userDaoFactory = new CDBUserDaoFactory(couchDbConnectorFactory);
        CDBKontoDatabaseFacade kontoDatabaseFacade = new CDBKontoDatabaseFacade(couchDbConnectorFactory,dbInstance);
        CDBEntryDaoFactory entryDaoFactory = new CDBEntryDaoFactory(couchDbConnectorFactory);
        CDBStandingOrderDaoFactory standingOrderDaoFactory = new CDBStandingOrderDaoFactory(couchDbConnectorFactory);

        CDBUserFacade userFacade = new CDBUserFacade(userDaoFactory, kontoDatabaseFacade);
        EntryFacade entryFacade = new CDBEntryFacade(userDaoFactory,entryDaoFactory);
        StandingOrderFacade standingOrderFacade = new CDBStandingOrderFacade(standingOrderDaoFactory,userDaoFactory);
        CDBStatisticFacade statisticFacade = new CDBStatisticFacade(userDaoFactory);

        JSONCouchDBWriter couchdbWriter = new JSONCouchDBWriter(userFacade, entryFacade, standingOrderFacade, statisticFacade);


        // File directory = new File("C:\\Users\\Johannes\\Desktop\\output"  + "\\johannes-1234");
        File inputDirectory = new File("C:\\Users\\Johannes\\Desktop\\output");

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


}
