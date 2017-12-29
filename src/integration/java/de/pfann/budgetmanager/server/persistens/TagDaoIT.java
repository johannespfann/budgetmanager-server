package de.pfann.budgetmanager.server.persistens;

import de.pfann.budgetmanager.server.model.AppUser;
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
    private AppUser firstUser;
    private AppUser secondUser;

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
        firstUser = new AppUser();
        firstUser.setName(NAME);
        firstUser.setEmail(EMAIL);
        firstUser.setPassword(PASSWORD);

        userDao.save(firstUser);

        secondUser = new AppUser();
        secondUser.setName("franz");
        secondUser.setEmail("franz@muster.com");
        secondUser.setPassword(PASSWORD);

        userDao.save(secondUser);
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
        tag.setAppUser(firstUser);

        // execute
        tagDao.save(tag);

        // validate
        Assert.assertEquals(1, tagDao.doGetAll().size());
    }

    @Test
    public void testDeleteTagsByUser(){
        // preparing
        Tag firstTag = new Tag();
        String firstTagName = "defaultTag";
        firstTag.setName(firstTagName);
        firstTag.setAppUser(firstUser);

        tagDao.save(firstTag);

        Tag secondTag = new Tag();
        secondTag.setName("defaultTag");
        secondTag.setAppUser(secondUser);

        tagDao.save(secondTag);

        Tag thirdTag = new Tag();
        thirdTag.setName("defaultTag");
        thirdTag.setAppUser(secondUser);

        tagDao.save(thirdTag);

        // execute
        tagDao.deleteAllByUser(secondUser);

        // validate
        Assert.assertEquals(firstTagName, tagDao.getAllByUser(firstUser).get(0).getName());
        Assert.assertEquals(0, tagDao.getAllByUser(secondUser).size());
    }

}
