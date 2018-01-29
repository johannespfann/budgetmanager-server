package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.persistens.daos.AppUserDao;
import de.pfann.budgetmanager.server.persistens.daos.CategoryDao;
import de.pfann.budgetmanager.server.persistens.daos.NoUserFoundException;
import de.pfann.budgetmanager.server.util.Util;

public class AppUserFacade {

    private AppUserDao userDao;

    private CategoryDao categoryDao;

    private EntryDao entryDao;

    private TagDao tagDao;

    public AppUserFacade(){
        userDao = AppUserDao.create();
        categoryDao = CategoryDao.create();
        entryDao = EntryDao.create();
        tagDao = TagDao.create();
    }

    public AppUserFacade(AppUserDao aUserDao, CategoryDao aCategoryDao){
        userDao = aUserDao;
        categoryDao = aCategoryDao;
    }

    public void createNewUser(String aName, String aEmail, String aPassword){
        AppUser user = new AppUser();
        user.setName(aName);
        user.setEmail(aEmail);
        user.setPassword(aPassword);

        userDao.save(user);

        Category defaultCategory = new Category();
        defaultCategory.setHash(Util.getUniueHash(100000,99999999));
        defaultCategory.setName("Allgemein");
        defaultCategory.setAppUser(user);

        categoryDao.save(defaultCategory);

        user.setDefaultCategory(defaultCategory);

        userDao.save(user);
    }

    public void activateUser(AppUser aUser){
        aUser.activate();
        userDao.save(aUser);
    }

    public void deactivateUser(AppUser aUser){
        aUser.deactivate();
        userDao.save(aUser);
    }

    public void deleteUser(AppUser aUser){
        // TODO NotImplemented
    }


    public AppUser getUserByNameOrEmail(String aIdentifier) {
        try {
            return userDao.getUserByNameOrEmail(aIdentifier);
        } catch (NoUserFoundException e) {
            e.printStackTrace();
        }
        // TODO dont use null as return value
        return null;
    }
}
