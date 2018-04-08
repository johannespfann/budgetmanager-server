package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.common.util.Util;
import de.pfann.budgetmanager.server.persistens.model.AppUser;
import de.pfann.budgetmanager.server.persistens.model.Category;


import java.util.List;

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

    public void createNewUser(AppUser aUser){
        userDao.save(aUser);

        Category defaultCategory = new Category();
        defaultCategory.setHash(Util.getUniueHash(100000,99999999));
        defaultCategory.setName("Allgemein");
        defaultCategory.setAppUser(aUser);

        categoryDao.save(defaultCategory);

        aUser.setDefaultCategory(defaultCategory);

        userDao.save(aUser);
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

    public void updateUser(AppUser aAppUser) {
        userDao.save(aAppUser);
    }

    public List<AppUser> getAllUser(){
        return userDao.getAll();
    }
}
