package de.pfann.budgetmanager.server.persistens;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.model.Tag;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TagDaoIT {

    /**
     * properties
     */

    private static final String NAME = "max muster";
    private static final String EMAIL = "max@muster.de";
    private static final String PASSWORD = "keymuster";

    /**
     * Helper
     */

    private AppUserDao userDao;
    private AppUser user;

    /**
     * class under test
     */

    private TagDao tagDao;


    @Before
    public void setUp(){
        // Setup db befor each test
        SessionDistributor.createForIT();

        userDao = AppUserDao.create();
        tagDao = TagDao.create();

        // Create default User
        user = new AppUser();
        user.setName(NAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        userDao.save(user);
    }

    @After
    public void cleanUp(){
        // close db after each test
        SessionDistributor.closeSessions();
    }

    @Test
    public void testCreateCategory(){
        // preparing
        Tag tag = new Tag();
        tag.setName("defaultTag");
        tag.setAppUser(user);

        // execute
        tagDao.save(tag);

        // validate
        Assert.assertEquals(1, tagDao.doGetAll().size());

    }

}
