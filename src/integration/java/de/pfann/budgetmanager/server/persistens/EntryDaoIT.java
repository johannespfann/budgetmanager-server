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
    public static final String Name_SECOND = "franz";
    public static final String NAME_SECOND = Name_SECOND;
    public static final String EMAIL_SECOND = "franz@muster.de";

    /**
     * Helper
     */

    private AppUserDao userDao;
    private AppUser firstUser;
    private AppUser secondUser;

    private CategoryDao categoryDao;
    private Category firstCategory;
    private Category secondCategory;

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

        // Create and save Users
        firstUser = new AppUser();
        firstUser.setName(NAME);
        firstUser.setEmail(EMAIL);
        firstUser.setPassword(PASSWORD);

        userDao.save(firstUser);

        secondUser = new AppUser();
        secondUser.setName(NAME_SECOND);
        secondUser.setEmail(EMAIL_SECOND);
        secondUser.setPassword(PASSWORD);

        userDao.save(secondUser);


        // Create and save Categories

        firstCategory = new Category();
        firstCategory.setName("firstCategory");
        firstCategory.setHash("hash123");
        firstCategory.setAppUser(firstUser);

        categoryDao.save(firstCategory);

        secondCategory = new Category();
        secondCategory.setHash("hash234");
        secondCategory.setName("secondCategory");
        secondCategory.setAppUser(secondUser);

        categoryDao.save(secondCategory);
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
        entry.setCategory(firstCategory);
        entry.setAppUser(firstUser);

        // execute
        entryDao.save(entry);

        // validate
        Assert.assertEquals(1, entryDao.doGetAll().size());
    }

    public void testGetAllByUser(){
        // Preparing entry of firstUser
        Entry entry = new Entry();
        entry.setAmount(123);
        entry.setHash("hash123");
        entry.setMemo("memo");
        entry.setCategory(firstCategory);
        entry.setAppUser(firstUser);

        entryDao.save(entry);

        // Praparing entry of secondUser

        Entry secondEntry = new Entry();
        secondEntry.setAmount(123);
        secondEntry.setHash("hash223");
        secondEntry.setMemo("memo");
        secondEntry.setCategory(secondCategory);
        secondEntry.setAppUser(secondUser);

        entryDao.save(secondEntry);

        // execute
        Assert.assertEquals(1, entryDao.getAllByUser(secondUser).size());
        Assert.assertEquals("hash223", entryDao.getAllByUser(secondUser).get(0).getHash());
    }

}
