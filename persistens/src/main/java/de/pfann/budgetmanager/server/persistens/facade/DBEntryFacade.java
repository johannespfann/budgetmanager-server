package de.pfann.budgetmanager.server.persistens.facade;

import de.pfann.budgetmanager.server.common.facade.V2EntryFacade;
import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.V2Entry;
import de.pfann.budgetmanager.server.persistens.dao.DBEntryDao;
import de.pfann.budgetmanager.server.persistens.dao.DBEntryDaoFactory;
import de.pfann.budgetmanager.server.persistens.model.CDBEntry;
import de.pfann.budgetmanager.server.persistens.model.CDBEntryFactory;
import de.pfann.budgetmanager.server.persistens.model.CDBKonto;
import de.pfann.budgetmanager.server.persistens.util.CDBEntryId;
import de.pfann.budgetmanager.server.persistens.util.CDBKontoDatabaseId;

import java.util.LinkedList;
import java.util.List;

public class DBEntryFacade implements V2EntryFacade {

    private DBEntryDaoFactory entryDaoFactory;

    public DBEntryFacade(DBEntryDaoFactory aEntryDaoFactory) {
        entryDaoFactory = aEntryDaoFactory;
    }

    @Override
    public void save(Account aAccount, V2Entry aEntry) {
        DBEntryDao entryDao = getEntryDao(aAccount);
        CDBEntryId entryId = CDBEntryId.createBuilder()
                .withHash(aEntry.getHash())
                .withUsername(aAccount.getOwner())
                .build();
        CDBEntry cdbEntry = CDBEntryFactory.createCDBEntry(aEntry);
        cdbEntry.setId(entryId.toString());
        entryDao.add(cdbEntry);
    }

    @Override
    public void delete(Account aAccount, String aHash) {
        DBEntryDao entryDao = getEntryDao(aAccount);
        CDBEntry entry = getCDBEntry(aAccount, aHash);
        entryDao.remove(entry);
    }

    @Override
    public void update(Account aAccount, V2Entry aEntry) {
        DBEntryDao entryDao = getEntryDao(aAccount);
        CDBEntry cdbEntry = getCDBEntry(aAccount, aEntry.getHash());
        CDBEntry updatedCDBEntry = CDBEntryFactory.updateCDBEntry(cdbEntry, aEntry);
        entryDao.update(updatedCDBEntry);
    }

    @Override
    public List<V2Entry> getEntries(Account aAccount) {
        DBEntryDao entryDao = getEntryDao(aAccount);
        List<CDBEntry> entries = entryDao.getAll();

        List<V2Entry> v2Entries = new LinkedList<>();

        for (CDBEntry cdbEntry : entries) {
            V2Entry v2Entry = CDBEntryFactory.createEntry(cdbEntry);
            v2Entries.add(v2Entry);
        }

        System.out.println("All entries: " + v2Entries.size());
        return v2Entries;
    }

    @Override
    public V2Entry getEntry(Account aAccount, String aHash) {
        CDBEntry cdbEntry = getCDBEntry(aAccount, aHash);
        return CDBEntryFactory.createEntry(cdbEntry);
    }


    public CDBEntry getCDBEntry(Account aAccount, String aHash) {
        DBEntryDao entryDao = getEntryDao(aAccount);
        CDBEntryId entryId = CDBEntryId.createBuilder()
                .withHash(aHash)
                .withUsername(aAccount.getOwner())
                .build();
        return entryDao.get(entryId.toString());
    }

    private DBEntryDao getEntryDao(Account aAccount) {
        CDBKontoDatabaseId kontodbId = CDBKontoDatabaseId.builder()
                .withKontoHash(aAccount.getHash())
                .withUsername(aAccount.getOwner())
                .withObjectTyp(CDBKonto.ENTRY_KONTO)
                .build();
        return entryDaoFactory.createDao(kontodbId.toString());
    }

}
