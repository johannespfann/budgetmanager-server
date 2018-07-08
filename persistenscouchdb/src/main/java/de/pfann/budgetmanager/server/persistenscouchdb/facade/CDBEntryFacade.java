package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.EntryFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.Tag;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBEntryDao;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBEntryDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDao;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBEntry;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBKonto;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBUser;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBEntryId;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBKontoDatabaseId;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBUserId;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBEntryTransformer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class CDBEntryFacade implements EntryFacade {

    private CDBEntryDaoFactory entryDaoFactory;
    private CDBUserDaoFactory userDaoFactory;

    private CDBUserDao userDao;

    public CDBEntryFacade(CDBUserDaoFactory aUserDaoFactory, CDBEntryDaoFactory aEntryDaoFactory){
        entryDaoFactory = aEntryDaoFactory;
        userDaoFactory = aUserDaoFactory;
        userDao = userDaoFactory.createDao();
    }

    @Override
    public List<Entry> getEntries(AppUser aUser) {
        CDBUserId userId = CDBUserId.create(aUser.getName());
        CDBUser cdbUser = userDao.get(userId.toString());

        CDBKontoDatabaseId kontodbId = CDBKontoDatabaseId.builder()
                .withKontoHash(cdbUser.getKontos().get(0).getHash())
                .withUsername(cdbUser.getUsername())
                .withObjectTyp(CDBKonto.ENTRY_KONTO)
                .build();

        CDBEntryDao cdbEntryDao = entryDaoFactory.createDao(kontodbId.toString());

        List<CDBEntry> cdbEntries = cdbEntryDao.getAll();
        List<Entry> entries = new ArrayList<>();

        for(CDBEntry cdbEntry : cdbEntries) {
            Entry entry = CDBEntryTransformer.createEntry(cdbEntry);
            entries.add(entry);
        }

        return entries;
    }

    @Override
    public void persistEntry(Entry aEntry) {
        CDBUserId userId = CDBUserId.create(aEntry.getAppUser().getName());
        CDBUser cdbUser = userDao.get(userId.toString());
        CDBKonto konto = cdbUser.getKontos().get(0);

        /**
         * find right konto
         */
        CDBKontoDatabaseId kontodbId = CDBKontoDatabaseId.builder()
                .withUsername(cdbUser.getUsername())
                .withKontoHash(konto.getHash())
                .withObjectTyp(CDBKonto.ENTRY_KONTO)
                .build();

        CDBEntryDao entryDao = entryDaoFactory.createDao(kontodbId.toString());

        CDBEntryId entryId = CDBEntryId.createBuilder()
                .withHash(aEntry.getHash())
                .withUsername(aEntry.getAppUser().getName())
                .build();

        CDBEntry cdbEntry = new CDBEntry();
        cdbEntry.setId(entryId.toString());
        cdbEntry = CDBEntryTransformer.updateCDBEntry(aEntry,cdbEntry);

        entryDao.add(cdbEntry);
    }

    @Override
    public void deleteEntry(AppUser aUser, Entry aEntry) {
        CDBUserId userId = CDBUserId.create(aUser.getName());
        CDBUser cdbUser = userDao.get(userId.toString());
        CDBKonto konto = cdbUser.getKontos().get(0);

        CDBKontoDatabaseId kontodbId = CDBKontoDatabaseId.builder()
                .withUsername(cdbUser.getUsername())
                .withKontoHash(konto.getHash())
                .withObjectTyp(CDBKonto.ENTRY_KONTO)
                .build();
        CDBEntryDao entryDao = entryDaoFactory.createDao(kontodbId.toString());

        CDBEntryId entryId = CDBEntryId.createBuilder()
                .withHash(aEntry.getHash())
                .withUsername(aUser.getName())
                .build();

        CDBEntry cdbEntry = entryDao.get(entryId.toString());
        entryDao.remove(cdbEntry);
    }

    @Override
    public Entry getEntry(AppUser aUser, String aHash) {
        CDBUserId userId = CDBUserId.create(aUser.getName());
        CDBUser cdbUser = userDao.get(userId.toString());
        CDBKonto konto = cdbUser.getKontos().get(0);

        CDBKontoDatabaseId kontodbId = CDBKontoDatabaseId.builder()
                .withUsername(cdbUser.getUsername())
                .withKontoHash(konto.getHash())
                .withObjectTyp(CDBKonto.ENTRY_KONTO)
                .build();
        CDBEntryDao entryDao = entryDaoFactory.createDao(kontodbId.toString());

        CDBEntryId entryId = CDBEntryId.createBuilder()
                .withHash(aHash)
                .withUsername(aUser.getName())
                .build();

        CDBEntry cdbEntry = entryDao.get(entryId.toString());

        Entry entry = CDBEntryTransformer.createEntry(cdbEntry);
        return entry;

    }


    @Override
    public void update(Entry aEntry) {
        CDBUserId userId = CDBUserId.create(aEntry.getAppUser().getName());
        CDBUser cdbUser = userDao.get(userId.toString());
        CDBKonto konto = cdbUser.getKontos().get(0);

        CDBKontoDatabaseId kontodbId = CDBKontoDatabaseId.builder()
                .withUsername(cdbUser.getUsername())
                .withKontoHash(konto.getHash())
                .withObjectTyp(CDBKonto.ENTRY_KONTO)
                .build();
        CDBEntryDao entryDao = entryDaoFactory.createDao(kontodbId.toString());

        CDBEntryId entryId = CDBEntryId.createBuilder()
                .withHash(aEntry.getHash())
                .withUsername(aEntry.getAppUser().getName())
                .build();

        CDBEntry cdbEntry = entryDao.get(entryId.toString());
        cdbEntry = CDBEntryTransformer.updateCDBEntry(aEntry,cdbEntry);

        entryDao.update(cdbEntry);
    }
}
