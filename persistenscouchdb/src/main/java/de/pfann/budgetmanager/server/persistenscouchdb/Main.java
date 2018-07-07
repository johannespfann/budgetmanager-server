package de.pfann.budgetmanager.server.persistenscouchdb;

import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.CDBKontoDatabaseFacade;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.CDBUserFacade;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.ObjectMapperFactory;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;

import java.net.MalformedURLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws MalformedURLException {
        HttpClient httpClient = new StdHttpClient.Builder()
                .url("http://localhost:5984")
                .build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);

        ObjectMapperFactory objectMapperFactory = new StdObjectMapperFactory();
        CouchDbConnectorFactory couchDbConnectorFactory = new CouchDbConnectorFactory(dbInstance,objectMapperFactory);

        CDBUserDaoFactory userDaoFactory = new CDBUserDaoFactory(couchDbConnectorFactory);
        CDBKontoDatabaseFacade kontoDatabaseFacade = new CDBKontoDatabaseFacade(couchDbConnectorFactory,dbInstance);

        AppUserFacade userFacade = new CDBUserFacade(userDaoFactory, kontoDatabaseFacade);


        AppUser appUser = new AppUser();
        appUser.setEmail("email");
        appUser.setName("johannes-1234");
        appUser.setPassword("key");

        userFacade.createNewUser(appUser);

        appUser.setPassword("johannes2");

        userFacade.updateUser(appUser);


        List<String> dbs = dbInstance.getAllDatabases();

        for(String db : dbs){
            System.out.println("delete " + db);
            dbInstance.deleteDatabase(db);
        }
    }
}
