package de.pfann.budgetmanager.server.persistens;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.persistens.core.SessionDistributor;
import de.pfann.budgetmanager.server.persistens.daos.AppUserDao;
import de.pfann.budgetmanager.server.persistens.daos.CategoryDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class CategoryDaoIT {

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

    private CategoryDao categoryDao;


    @Before
    public void setUp(){
        // Setup db befor each test
        SessionDistributor.createForIT();

        userDao = AppUserDao.create();
        categoryDao = CategoryDao.create();

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
        Category category = new Category();
        category.setName("defaultcategory");
        category.setHash("hash123");
        category.setAppUser(user);

        // execute
        categoryDao.save(category);

        // validate
        Assert.assertEquals(1, categoryDao.getAllByUser(user).size());

    }


}
