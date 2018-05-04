package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.model.AppUser;

import java.util.List;

public class AppUserFacade {

    private AppUserDao userDao;


    private EntryDao entryDao;

    private TagDao tagDao;

    public AppUserFacade(){
        userDao = AppUserDao.create();
        entryDao = EntryDao.create();
        tagDao = TagDao.create();
    }

    public AppUserFacade(AppUserDao aUserDao){
        userDao = aUserDao;
    }

    public void createNewUser(AppUser aUser){
        LogUtil.info(this.getClass(),"##### Save user" + aUser.getName());
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
