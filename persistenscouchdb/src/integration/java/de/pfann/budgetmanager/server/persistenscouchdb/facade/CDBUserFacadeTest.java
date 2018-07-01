package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.persistenscouchdb.core.BMObjectMapperFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBKontoDatabaseFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDao;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.ObjectMapperFactory;
import org.ektorp.impl.StdCouchDbInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.List;

public class CDBUserFacadeTest {

    /**
     *
     */

    private CouchDbInstance dbInstance;

    /**
     * class under test
     */

    private CDBUserFacade userFacade;

    @Before
    public void setUp() throws MalformedURLException {
        HttpClient httpClient = new StdHttpClient.Builder()
                .url("http://localhost:5984")
                .build();
        dbInstance = new StdCouchDbInstance(httpClient);
        ObjectMapperFactory objectMapperFactory = new BMObjectMapperFactory();
        CouchDbConnectorFactory couchDbConnectorFactory = new CouchDbConnectorFactory(dbInstance, objectMapperFactory);
        CDBUserDaoFactory factory = new CDBUserDaoFactory(couchDbConnectorFactory);
        CDBKontoDatabaseFactory kontoDBFactory = new CDBKontoDatabaseFactory(couchDbConnectorFactory);
        userFacade = new CDBUserFacade(factory, kontoDBFactory);
    }

    @Test
    public void testCreateNewUser(){
        AppUser user = new AppUser();
        user.setEmail("johannes@pfann.de");
        user.setName("johannes-1234");
        userFacade.createNewUser(user);

        AppUser persistedUser = userFacade.getUserByNameOrEmail(user.getName());
        System.out.println(persistedUser.getName());
        System.out.println(persistedUser.getEmail());
    }

    @Test
    public void test() {
        AppUser user = new AppUser();


    }


    @After
    public void cleanUp(){
        List<String> databases = dbInstance.getAllDatabases();

        for(String db : databases){
            dbInstance.deleteDatabase(db);
        }
    }

}
