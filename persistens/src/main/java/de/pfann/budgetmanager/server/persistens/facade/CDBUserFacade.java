package de.pfann.budgetmanager.server.persistens.facade;

import de.pfann.budgetmanager.server.common.facade.UserFacade;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.model.User;
import de.pfann.budgetmanager.server.persistens.dao.CDBUserDao;
import de.pfann.budgetmanager.server.persistens.dao.CDBUserDaoFactory;
import de.pfann.budgetmanager.server.persistens.util.CDBUserId;

import java.time.LocalDateTime;
import java.util.List;

public class CDBUserFacade implements UserFacade {

    private CDBUserDao CDBUserDao;

    public CDBUserFacade(CDBUserDaoFactory aCDBUserDaoFactory) {
        CDBUserDao = aCDBUserDaoFactory.createDao();
    }

    @Override
    public void createNewUser(User aUser) {

        CDBUserId userId = CDBUserId.create(aUser.getName());

        User user = new User();
        user.setId(userId.toString());
        user.deactivate();

        user.setName(aUser.getName());
        user.setEmails(aUser.getEmails());
        user.setCreatedAt(DateUtil.asDate(LocalDateTime.now()));
        user.setPassword(aUser.getPassword());

        CDBUserDao.add(user);
    }

    @Override
    public void activateUser(User aUser) {
        System.out.println("activateUser in CDBUserFacade");
        CDBUserId userId = CDBUserId.create(aUser.getName());
        User user = CDBUserDao.get(userId.toString());
        user.activate();
        CDBUserDao.update(user);
    }

    @Override
    public void deactivateUser(User aUser) {
        System.out.println("deactivateUser in CDBUserFacade");
        CDBUserId userId = CDBUserId.create(aUser.getName());
        User user = CDBUserDao.get(userId.toString());
        user.deactivate();
        CDBUserDao.update(user);
    }

    @Override
    public void deleteUser(User aUser) {
        System.out.println("deleteUser in CDBUserFacade");
        CDBUserId userId = CDBUserId.create(aUser.getName());
        User user = CDBUserDao.get(userId.toString());

        // TODO Get all kontos und lösche

        // TODO Get all foreign kontos und lösche die referenzen
    }


    @Override
    public boolean isEmailAlreadyExists(String aEmail) {
        List<User> users = CDBUserDao.getAll();

        for (User user : users) {
            if (foundEmail(aEmail, user)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void updateUser(User aUser) {
        CDBUserId userId = CDBUserId.create(aUser.getName());
        User newUser = CDBUserDao.get(userId.toString());

        newUser.setName(aUser.getName());
        newUser.setPassword(aUser.getPassword());
        newUser.setCreatedAt(aUser.getCreatedAt());
        newUser.setKontos(aUser.getKontos());
        newUser.setForeignKontos(aUser.getForeignKontos());
        newUser.setEmails(newUser.getEmails());
        newUser.setActivated(aUser.isActivated());

        CDBUserDao.update(newUser);
    }

    @Override
    public List<User> getAllUser() {
        return CDBUserDao.getAll();
    }

    @Override
    public User getUserByNameOrEmail(String aIdentifier) {
        System.out.println("getNameByNameOrEmail in CDBUserFacade");

        User user = getUserByEmail(aIdentifier);
        if (user == null) {
            CDBUserId userId = CDBUserId.create(aIdentifier);
            user = CDBUserDao.get(userId.toString());
        }
        return user;
    }

    private User getUserByEmail(String aIdentifier) {
        System.out.println("get all by " + aIdentifier);
        List<User> users = CDBUserDao.getAll();

        for (User user : users) {
            if (foundEmail(aIdentifier, user)) {
                return user;
            }
        }
        return null;
    }

    private boolean foundEmail(String aIdentifier, User user) {
        for (String email : user.getEmails()) {
            if (aIdentifier.equals(email)) {
                return true;
            }
        }
        return false;
    }
}
