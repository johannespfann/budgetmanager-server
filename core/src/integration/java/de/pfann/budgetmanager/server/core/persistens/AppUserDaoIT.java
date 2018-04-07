package de.pfann.budgetmanager.server.core.persistens;

import de.pfann.budgetmanager.server.core.model.AppUser;
import de.pfann.budgetmanager.server.core.persistens.core.SessionDistributor;
import de.pfann.budgetmanager.server.core.persistens.daos.AppUserDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AppUserDaoIT {


    /**
     * properties
     */

    public static final String NAME = "max muster";
    public static final String EMAIL = "max@muster.de";
    public static final String PASSWORD = "keymuster";

    /**
     * class under test
     */
    AppUserDao userDao;


    @Before
    public void setUp(){
        // Setup db befor each test
        SessionDistributor.createForIT();

        userDao = AppUserDao.create();
    }

    @After
    public void cleanUp(){
        // close db after each test
        SessionDistributor.closeSessions();
    }

    @Test
    public void testCreateAppUser(){
        // preparing
        AppUser user = new AppUser();
        user.setName(NAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        // execute
        userDao.save(user);

        // validate
        Assert.assertEquals(1, userDao.countAll());
    }


}
