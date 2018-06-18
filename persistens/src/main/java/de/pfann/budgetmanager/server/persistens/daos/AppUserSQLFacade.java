package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;

import java.util.List;

public class AppUserSQLFacade implements AppUserFacade {

    private AppUserDao userDao;


    private EntryDao entryDao;

    private TagDao tagDao;

    public AppUserSQLFacade(){
        userDao = AppUserDao.create();
        entryDao = EntryDao.create();
        tagDao = TagDao.create();
    }

    public AppUserSQLFacade(AppUserDao aUserDao){
        userDao = aUserDao;
    }

    @Override
    public void createNewUser(AppUser aUser){
        userDao.save(aUser);
    }

    @Override
    public void activateUser(AppUser aUser){
        aUser.activate();
        userDao.save(aUser);
    }

    @Override
    public void deactivateUser(AppUser aUser){
        aUser.deactivate();
        userDao.save(aUser);
    }

    @Override
    public void deleteUser(AppUser aUser){
        // TODO NotImplemented
    }


    @Override
    public AppUser getUserByNameOrEmail(String aIdentifier) {
        try {
            return userDao.getUserByNameOrEmail(aIdentifier);
        } catch (NoUserFoundException e) {
            e.printStackTrace();
        }
        // TODO dont use null as return value
        return null;
    }

    @Override
    public void updateUser(AppUser aAppUser) {
        userDao.save(aAppUser);
    }

    @Override
    public List<AppUser> getAllUser(){
        return userDao.getAll();
    }
}
