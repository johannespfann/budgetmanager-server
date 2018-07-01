package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.util.HashUtil;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBKontoDatabaseFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDao;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBKonto;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBUser;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBKontoDatabaseId;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBUserId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CDBUserFacade implements AppUserFacade {

    private CDBUserDao userDao;
    private CDBKontoDatabaseFactory kontoDatabaseFactory;

    public CDBUserFacade(CDBUserDaoFactory aUserDaoFactory, CDBKontoDatabaseFactory aKontoDatabaseFactory){
        userDao = aUserDaoFactory.createDao();
        kontoDatabaseFactory = aKontoDatabaseFactory;
    }

    @Override
    public void createNewUser(AppUser aUser) {

        /**
         * create new user
         * TODO konto muss angelegt werden -> registrierung des Kontos beim User?
         * TODO Statistik muss angelegt werden?
         */
        CDBUser cdbUser = new CDBUser();

        CDBUserId userId = CDBUserId.create(aUser.getName());

        cdbUser.setId(userId.toString());
        cdbUser.deactivate();
        cdbUser.addEmail(aUser.getEmail());
        cdbUser.setCreatedAt(LocalDateTime.now());
        cdbUser.setUsername(aUser.getName());
        cdbUser.setEncryptionText("");
        cdbUser.setPassword(aUser.getPassword());

        CDBKonto konto = new CDBKonto();
        konto.setHash(HashUtil.getEightDigitRandomId());
        konto.setName("Default");
        konto.setOwner(cdbUser.getUsername());

        cdbUser.addKonto(konto);

        String kontoDBName = CDBKontoDatabaseId.builder()
                .withUsername(cdbUser.getUsername())
                .withKontoHash(konto.getHash())
                .build()
                .toString();

        kontoDatabaseFactory.createDBIfExists(kontoDBName);

        userDao.add(cdbUser);
    }

    @Override
    public void activateUser(AppUser aUser) {

    }

    @Override
    public void deactivateUser(AppUser aUser) {

    }

    @Override
    public void deleteUser(AppUser aUser) {

    }

    @Override
    public AppUser getUserByNameOrEmail(String aIdentifier) {
        CDBUserId userId = CDBUserId.create(aIdentifier);
        CDBUser user = userDao.get(userId.toString());

        AppUser appUser = new AppUser();
        appUser.setName(user.getUsername());
        appUser.setEmail(user.getEmails().get(0));
        appUser.setEncrypted(user.isUSerEncrypted());
        appUser.setEncryptionText(user.getEncryptiontext());
        appUser.setPassword(user.getPassword());

        if(user.isActivated()) {
            appUser.activate();
        }

        return appUser;
    }

    @Override
    public void updateUser(AppUser aAppUser) {
        CDBUserId userId = CDBUserId.create(aAppUser.getName());
        CDBUser user = userDao.get(userId.toString());

        user.setUsername(aAppUser.getName());
        user.setPassword(aAppUser.getPassword());
        user.setEncryptionText(aAppUser.getEncryptionText());

        List<String> emails = user.getEmails();

        if(!emails.contains(aAppUser.getEmail())){
            emails.clear();
            emails.add(aAppUser.getEmail());
        }

        if(aAppUser.isActivated()){
            user.activate();
        }
    }

    @Override
    public List<AppUser> getAllUser() {
        List<AppUser> appUsers = new ArrayList<>();

        return null;
    }
}
