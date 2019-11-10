package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.EntryFacade;
import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBEntryDao;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBEntryDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBKonto;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBEntryId;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBKontoDatabaseId;

import java.util.List;

public class CDBEntryFacade implements EntryFacade {

    private CDBEntryDaoFactory entryDaoFactory;

    public CDBEntryFacade(CDBEntryDaoFactory aEntryDaoFactory) {
        entryDaoFactory = aEntryDaoFactory;
    }

    @Override
    public void save(Account aAccount, Entry aEntry) {
        CDBEntryDao entryDao = getEntryDao(aAccount);
        CDBEntryId entryId = CDBEntryId.createBuilder()
                .withHash(aEntry.getHash())
                .withUsername(aAccount.getOwner())
                .build();

        aEntry.setId(entryId.toString());
        entryDao.add(aEntry);
    }

    @Override
    public void delete(Account aAccount, String aHash) {
        CDBEntryDao entryDao = getEntryDao(aAccount);
        Entry entry = getEntry(aAccount,aHash);
        entryDao.remove(entry);
    }

    @Override
    public void update(Account aAccount, Entry aEntry) {
        CDBEntryDao entryDao = getEntryDao(aAccount);

        Entry entry = getEntry(aAccount,aEntry.getHash());
        Entry updatedEntry = new Entry(
                aEntry.getHash(),
                aEntry.getUsername(),
                aEntry.getCreatedAt(),
                aEntry.getData());
        updatedEntry.setId(entry.getId());
        updatedEntry.setRev(entry.getRev());

        entryDao.update(updatedEntry);
    }

    @Override
    public List<Entry> getEntries(Account aAccount) {
        CDBEntryDao entryDao = getEntryDao(aAccount);
        List<Entry> entries =  entryDao.getAll();
        return entries;
    }

    @Override
    public Entry getEntry(Account aAccount, String aHash) {
        CDBEntryDao entryDao = getEntryDao(aAccount);
        CDBEntryId entryId = CDBEntryId.createBuilder()
                .withHash(aHash)
                .withUsername(aAccount.getOwner())
                .build();
        return entryDao.get(entryId.toString());
    }


    private CDBEntryDao getEntryDao(Account aAccount) {
        CDBKontoDatabaseId kontodbId = CDBKontoDatabaseId.builder()
                .withKontoHash(aAccount.getHash())
                .withUsername(aAccount.getOwner())
                .withObjectTyp(CDBKonto.ENTRY_KONTO)
                .build();
        return entryDaoFactory.createDao(kontodbId.toString());
    }

}
