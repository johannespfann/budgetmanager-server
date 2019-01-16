package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.UserFacade;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.model.User;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.UserDao;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.UserDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBUserId;

import java.time.LocalDateTime;
import java.util.List;

public class UserFacadeImpl implements UserFacade {

    private UserDao userDao;

    public UserFacadeImpl(UserDaoFactory aUserDaoFactory) {
        userDao = aUserDaoFactory.createDao();
    }

    @Override
    public void createNewUser(User aUser) {
        User user = new User();
        CDBUserId userId = CDBUserId.create(aUser.getName());

        user.setId(userId.toString());
        user.deactivate();

        user.setName(aUser.getName());
        user.setEmails(aUser.getEmails());
        user.setCreatedAt(DateUtil.asDate(LocalDateTime.now()));
        user.setPassword(aUser.getPassword());

        userDao.add(user);
    }

    @Override
    public void activateUser(User aUser) {
        System.out.println("activateUser in UserFacadeImpl");
        CDBUserId userId = CDBUserId.create(aUser.getName());
        User user = userDao.get(userId.toString());
        user.activate();
        userDao.update(user);
    }

    @Override
    public void deactivateUser(User aUser) {
        System.out.println("deactivateUser in UserFacadeImpl");
        CDBUserId userId = CDBUserId.create(aUser.getName());
        User user = userDao.get(userId.toString());
        user.deactivate();
        userDao.update(user);
    }

    @Override
    public void deleteUser(User aUser) {
        System.out.println("deleteUser in UserFacadeImpl");
        CDBUserId userId = CDBUserId.create(aUser.getName());
        User user = userDao.get(userId.toString());

        // TODO Get all kontos und lösche

        // TODO Get all foreign kontos und lösche die referenzen
    }

    @Override
    public User getUserByNameOrEmail(String aIdentifier) {
        System.out.println("getNameByNameOrEmail in UserFacadeImpl");
        User user = getUserByEmail(aIdentifier);

        if(user == null) {
            CDBUserId userId = CDBUserId.create(aIdentifier);
            user = userDao.get(userId.toString());
        }

        return user;
    }

    @Override
    public boolean isEmailAlreadyExists(String aEmail) {
        List<User> users = userDao.getAll();

        for(User user : users) {
            if(foundEmail(aEmail,user)){
                return true;
            }
        }

        return false;
    }

    @Override
    public void updateUser(User aUser) {
        System.out.println("update User in UserFacadeImpl");
        CDBUserId userId = CDBUserId.create(aUser.getName());
        User newUser = userDao.get(userId.toString());

        newUser.setName(aUser.getName());
        newUser.setPassword(aUser.getPassword());
        newUser.setStatistic(aUser.getStatistic());
        newUser.setCreatedAt(aUser.getCreatedAt());
        newUser.setKontos(aUser.getKontos());
        newUser.setForeignKontos(aUser.getForeignKontos());
        newUser.setEmails(newUser.getEmails());
        newUser.setActivated(aUser.isActivated());

        userDao.update(newUser);
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAll();
    }

    private User getUserByEmail(String aIdentifier) {
        List<User> users = userDao.getAll();

        for(User user : users) {

            if (foundEmail(aIdentifier, user)){
                return user;
            }

        }

        return null;
    }

    private boolean foundEmail(String aIdentifier, User user) {
        for(String email : user.getEmails()) {
            if(aIdentifier.equals(email)){
                return true;
            }
        }
        return false;
    }
}
