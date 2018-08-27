package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.HashUtil;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDao;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBKonto;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBUser;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBKontoDatabaseId;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBUserId;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBUserTransformer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CDBUserFacade implements AppUserFacade {

    private CDBUserDao userDao;
    private CDBKontoDatabaseFacade kontoDatabaseFactory;

    public CDBUserFacade(CDBUserDaoFactory aUserDaoFactory, CDBKontoDatabaseFacade aKontoDatabaseFactory){
        userDao = aUserDaoFactory.createDao();
        kontoDatabaseFactory = aKontoDatabaseFactory;
    }

    @Override
    public void createNewUser(AppUser aUser) {
        CDBUser cdbUser = new CDBUser();
        CDBUserId userId = CDBUserId.create(aUser.getName());

        cdbUser.setId(userId.toString());
        cdbUser.deactivate();
        cdbUser.addEmail(aUser.getEmail());
        cdbUser.setCreatedAt(DateUtil.asDate(LocalDateTime.now()));
        cdbUser.setUsername(aUser.getName());
        cdbUser.setEncryptionText("");
        cdbUser.setPassword(aUser.getPassword());

        CDBKonto konto = new CDBKonto();
        konto.setHash(HashUtil.getEightDigitRandomId());
        konto.setName("Default");
        konto.setOwner(cdbUser.getUsername());

        cdbUser.addKonto(konto);

        userDao.add(cdbUser);

        String entryKontoName = CDBKontoDatabaseId.builder()
                .withUsername(cdbUser.getUsername())
                .withKontoHash(konto.getHash())
                .withObjectTyp(CDBKonto.ENTRY_KONTO)
                .build()
                .toString();
        kontoDatabaseFactory.createDBIfExists(entryKontoName);

        String orderKontoName = CDBKontoDatabaseId.builder()
                .withUsername(cdbUser.getUsername())
                .withKontoHash(konto.getHash())
                .withObjectTyp(CDBKonto.ENTRY_KONTO)
                .build()
                .toString();
        kontoDatabaseFactory.createDBIfExists(orderKontoName);
    }

    @Override
    public void activateUser(AppUser aUser) {
        CDBUserId userId = CDBUserId.create(aUser.getName());
        CDBUser cdbUser = userDao.get(userId.toString());
        cdbUser.activate();
        userDao.update(cdbUser);
    }

    @Override
    public void deactivateUser(AppUser aUser) {
        CDBUserId userId = CDBUserId.create(aUser.getName());
        CDBUser cdbUser = userDao.get(userId.toString());
        cdbUser.deactivate();
        userDao.update(cdbUser);
    }

    @Override
    public void deleteUser(AppUser aUser) {
        CDBUserId userId = CDBUserId.create(aUser.getName());
        CDBUser cdbUser = userDao.get(userId.toString());

        List<CDBKonto> konten = cdbUser.getKontos();

        for(CDBKonto konto : konten){
            CDBKontoDatabaseId kontoId = CDBKontoDatabaseId.builder()
                    .withKontoHash(konto.getHash())
                    .withUsername(konto.getOwner())
                    .build();
            kontoDatabaseFactory.deleteDBIfExists(kontoId.toString());
        }

        // TODO durchsuche alle User ob die noch referensen haben von foreignUser und deren Konten

        userDao.remove(cdbUser);
    }

    @Override
    public AppUser getUserByNameOrEmail(String aIdentifier) {
        CDBUserId userId = CDBUserId.create(aIdentifier);
        CDBUser cdbUser = userDao.get(userId.toString());
        AppUser appUser = CDBUserTransformer.createAppUser(cdbUser);
        return appUser;
    }

    @Override
    public void updateUser(AppUser aAppUser) {
        CDBUserId userId = CDBUserId.create(aAppUser.getName());
        CDBUser cdbUser = userDao.get(userId.toString());
        cdbUser = CDBUserTransformer.updateCDBUser(aAppUser,cdbUser);
        userDao.update(cdbUser);
    }

    public void updateUser(CDBUser aUser) {
        CDBUserId userId = CDBUserId.create(aUser.getUsername());
        CDBUser cdbUser = userDao.get(userId.toString());
        System.out.println("User" + cdbUser);
        cdbUser.setUsername(aUser.getUsername());
        cdbUser.setPassword(aUser.getPassword());
        cdbUser.setTagStatistics(aUser.getTagStatistics());
        cdbUser.setEncryptionText(aUser.getEncryptiontext());

        if(aUser.isActivated()){
            cdbUser.activate();
        }

        userDao.update(cdbUser);
    }

    @Override
    public List<AppUser> getAllUser() {
        List<AppUser> appUsers = new ArrayList<>();
        List<CDBUser> cdbUsers = userDao.getAll();
        for(CDBUser cdbUser : cdbUsers){
            AppUser appUser = CDBUserTransformer.createAppUser(cdbUser);
            appUsers.add(appUser);
        }
        return appUsers;
    }
}
