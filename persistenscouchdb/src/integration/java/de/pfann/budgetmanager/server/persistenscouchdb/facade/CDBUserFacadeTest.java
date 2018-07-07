package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBUser;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.ObjectMapperFactory;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.List;

public class CDBUserFacadeTest {

    private CouchDbInstance dbInstance;

    private AppUser appUser;
    private CDBUser cdbUser;

    private String name;
    private String email;
    private String password;
    private boolean activated;
    private String encryptionText;

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
        ObjectMapperFactory objectMapperFactory = new StdObjectMapperFactory();
        CouchDbConnectorFactory couchDbConnectorFactory = new CouchDbConnectorFactory(dbInstance, objectMapperFactory);
        CDBUserDaoFactory factory = new CDBUserDaoFactory(couchDbConnectorFactory);
        CDBKontoDatabaseFacade kontoDBFactory = new CDBKontoDatabaseFacade(couchDbConnectorFactory, dbInstance);
        userFacade = new CDBUserFacade(factory, kontoDBFactory);

        name = "johannes";
        email = "johannes@pfann.de";
        activated = true;
        password = "keymaster";
        encryptionText = "text";

        appUser = new AppUser();
        appUser.setName(name);
        appUser.setEmail(email);
        appUser.setPassword(password);
        appUser.deactivate();
        appUser.setEncryptionText(encryptionText);

        cdbUser = new CDBUser();
        cdbUser.setUsername(name);
        cdbUser.setPassword(password);
        cdbUser.addEmail(email);
        cdbUser.deactivate();
        cdbUser.setEncryptionText(encryptionText);
    }

    @Test
    public void testCreateNewUser(){
        userFacade.createNewUser(appUser);

        AppUser persistedUser = userFacade.getUserByNameOrEmail(appUser.getName());

        Assert.assertEquals(persistedUser.getName(), name);
        Assert.assertEquals(persistedUser.getEmail(), email);
        Assert.assertEquals(persistedUser.getPassword(), password);
    }

    @Test
    public void test() {

    }


    @After
    public void cleanUp(){
        List<String> databases = dbInstance.getAllDatabases();

        for(String db : databases){
            dbInstance.deleteDatabase(db);
        }
    }

}
