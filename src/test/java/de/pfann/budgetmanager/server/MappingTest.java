package de.pfann.budgetmanager.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.persistens.core.SessionDistributor;
import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.daos.CategoryFacade;
import de.pfann.budgetmanager.server.persistens.daos.EntryFacade;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MappingTest {

    private AppUserFacade userFacade;

    private CategoryFacade categoryFacade;

    private EntryFacade entryFacade;

    private ObjectMapper mapper;

    @Before
    public void setUp(){
        // Setup db befor each test
        SessionDistributor.createForIT();

        TestClass testClass = new TestClass();
        testClass.persistEnviroment();

        categoryFacade = new CategoryFacade();
        userFacade = new AppUserFacade();
        entryFacade = new EntryFacade();

        mapper = new ObjectMapper();

    }

    @Test
    public void test1(){
        AppUser user = userFacade.getUserByNameOrEmail("johannes@pfann.de");

        System.out.println(user.getName());
        System.out.println(user.getDefaultCategory().getName());

        List<Entry> entries = entryFacade.getEntries(user);

        String entriesJSON = "";

        try {
            entriesJSON = mapper.writeValueAsString(entries);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(entriesJSON);


    }

    @After
    public void cleanUp(){

    }
}
