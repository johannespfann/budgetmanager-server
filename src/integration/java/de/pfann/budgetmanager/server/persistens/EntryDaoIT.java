package de.pfann.budgetmanager.server.persistens;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.model.Entry;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EntryDaoIT {

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

    private CategoryDao categoryDao;
    private Category category;

    /**
     * class under test
     */

    private EntryDao entryDao;


    @Before
    public void setUp(){
        // Setup db befor each test
        SessionDistributor.createForIT();

        userDao = AppUserDao.create();
        categoryDao = CategoryDao.create();
        entryDao = EntryDao.create();

        // Create and save User
        user = new AppUser();
        user.setName(NAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        userDao.save(user);

        // Create and save Category

        category = new Category();
        category.setName("category");
        category.setHash("hash123");
        category.setAppUser(user);

        categoryDao.save(category);
    }

    @After
    public void cleanUp(){
        // close db after each test
        SessionDistributor.closeSessions();
    }

    @Test
    public void testCreateCategory(){
        // preparing
        Entry entry = new Entry();
        entry.setAmount(123);
        entry.setHash("hash123");
        entry.setMemo("memo");
        entry.setCategory(category);
        entry.setAppUser(user);

        // execute
        entryDao.save(entry);

        // validate
        Assert.assertEquals(1, entryDao.doGetAll().size());
    }

}
