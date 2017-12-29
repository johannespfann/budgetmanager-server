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
    public static final String HASH_FIRST_ENTRY = "hash123";
    public static final String MEMO = "memo";

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
        Assert.assertEquals(1, entryDao.getAll().size());
    }

    @Test
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

    @Test
    public void testGetEntryByHash(){
        // Preparing entry of firstUser
        Entry entry = new Entry();
        entry.setAmount(123);
        entry.setHash(HASH_FIRST_ENTRY);
        entry.setMemo("memo");
        entry.setCategory(firstCategory);
        entry.setAppUser(firstUser);

        // execute
        entryDao.save(entry);
        Entry newEntry = entryDao.getEntryByHash(HASH_FIRST_ENTRY).get(0);

        // validate
        Assert.assertEquals(HASH_FIRST_ENTRY, newEntry.getHash());
        Assert.assertEquals(true, entryDao.getEntryByHash(HASH_FIRST_ENTRY).size() == 1);
    }

    @Test
    public void testUpdateEntry(){
        // Preparing
        Entry entry = new Entry();
        entry.setAmount(123);
        entry.setHash(HASH_FIRST_ENTRY);
        entry.setMemo(MEMO);
        entry.setCategory(firstCategory);
        entry.setAppUser(firstUser);

        // execute
        entryDao.save(entry);
        Entry newEntry = entryDao.getEntryByHash(HASH_FIRST_ENTRY).get(0);

        String new_memo = "new memo";
        newEntry.setMemo(new_memo);

        entryDao.save(newEntry);

        // validate
        Assert.assertEquals(true, entryDao.getEntryByHash(HASH_FIRST_ENTRY).size() == 1);
        Assert.assertEquals(new_memo, entryDao.getEntryByHash(HASH_FIRST_ENTRY).get(0).getMemo());
    }

    @Test(expected = DataHandlerException.class)
    public void testUniqueHash() throws DataHandlerException{
        // Preparing
        Entry entry = new Entry();
        entry.setAmount(123);
        entry.setHash(HASH_FIRST_ENTRY);
        entry.setMemo(MEMO);
        entry.setCategory(firstCategory);
        entry.setAppUser(firstUser);

        Entry entryDuplicate = new Entry();
        entryDuplicate.setAmount(123);
        entryDuplicate.setHash(HASH_FIRST_ENTRY);
        entryDuplicate.setMemo(MEMO);
        entryDuplicate.setCategory(firstCategory);
        entryDuplicate.setAppUser(firstUser);

        // execute && validate
        entryDao.save(entry);
        entryDao.save(entryDuplicate);
    }

}
