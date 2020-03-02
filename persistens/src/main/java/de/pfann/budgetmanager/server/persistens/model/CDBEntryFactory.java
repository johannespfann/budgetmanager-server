package de.pfann.budgetmanager.server.persistens.model;

import de.pfann.budgetmanager.server.model.V2Entry;

public class CDBEntryFactory {

    public static CDBEntry createCDBEntry(V2Entry v2Entry) {
        CDBEntry cdbEntry = new CDBEntry();
        cdbEntry.setHash(v2Entry.getHash());
        cdbEntry.setAmount(v2Entry.getAmount());
        cdbEntry.setCurrency(v2Entry.getCurrency());
        cdbEntry.setCreated_At(v2Entry.getCreatedAt());
        cdbEntry.setMemo(v2Entry.getMemo());
        cdbEntry.setUsername(v2Entry.getUsername());
        cdbEntry.setTags(v2Entry.getTags());
        return cdbEntry;
    }

    public static CDBEntry updateCDBEntry(CDBEntry aCdbEntry, V2Entry v2Entry) {
        CDBEntry cdbEntry = new CDBEntry();
        cdbEntry.setId(aCdbEntry.getId());
        cdbEntry.setRev(aCdbEntry.getRev());
        cdbEntry.setHash(v2Entry.getHash());
        cdbEntry.setAmount(v2Entry.getAmount());
        cdbEntry.setCurrency(v2Entry.getCurrency());
        cdbEntry.setCreated_At(v2Entry.getCreatedAt());
        cdbEntry.setMemo(v2Entry.getMemo());
        cdbEntry.setUsername(v2Entry.getUsername());
        cdbEntry.setTags(v2Entry.getTags());
        return cdbEntry;
    }

    public static V2Entry createEntry(CDBEntry aCDBEntry) {
        V2Entry entry = new V2Entry(
                aCDBEntry.getHash(),
                aCDBEntry.getUsername(),
                aCDBEntry.getCreated_At(),
                aCDBEntry.getAmount(),
                aCDBEntry.getCurrency(),
                aCDBEntry.getMemo(),
                aCDBEntry.getTags());
        return entry;
    }


}
