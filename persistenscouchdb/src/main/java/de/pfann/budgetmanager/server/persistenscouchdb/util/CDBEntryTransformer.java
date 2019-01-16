package de.pfann.budgetmanager.server.persistenscouchdb.util;

import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.Tag;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBEntry;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBTag;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CDBEntryTransformer {

    public static Entry createEntry(CDBEntry aCDBEntry){
        Entry entry = new Entry();

        entry.setAmount(aCDBEntry.getAmount());
        entry.setMemo(aCDBEntry.getMemo());
        entry.setHash(aCDBEntry.getHash());
        entry.setCreated_at(aCDBEntry.getCreated_at());
        entry.setCurrency(aCDBEntry.getCurrency());

        List<Tag> tags = new LinkedList<>();

        for(CDBTag cdbTag : aCDBEntry.getTags()){
            Tag tag = new Tag();
            tag.setName(cdbTag.getName());
            tags.add(tag);
        }

        entry.setTags(tags);

        return entry;
    }

    public static CDBEntry updateCDBEntry(Entry aEntry, CDBEntry aCDBEntry){
        aCDBEntry.setUsername(aEntry.getAppUser().getName());
        aCDBEntry.setMemo(aEntry.getMemo());
        aCDBEntry.setHash(aEntry.getHash());
        aCDBEntry.setAmount(aEntry.getAmount());
        aCDBEntry.setCurrency(aEntry.getCurrency());
        aCDBEntry.setCreated_at(aEntry.getCreated_at());
        aCDBEntry.setData("");

        List<CDBTag> cdbTags = new LinkedList<>();

        for(Tag tag : aEntry.getTags()){
            CDBTag cdbtag = new CDBTag();
            cdbtag.setName(tag.getName());
            cdbTags.add(cdbtag);
        }

        aCDBEntry.setTags(cdbTags);
        return aCDBEntry;
    }

}
