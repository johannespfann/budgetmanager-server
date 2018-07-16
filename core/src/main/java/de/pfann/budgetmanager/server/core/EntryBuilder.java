package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.Tag;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.HashUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntryBuilder {

    private final AppUser user;
    private List<Tag> tags;
    private String amount;
    private String memo;
    private Date createdAt;

    private EntryBuilder(AppUser aUser){
        user = aUser;
        tags= new ArrayList<>();
    }

    public static EntryBuilder createBuilder(AppUser aUser){
        return new EntryBuilder(aUser);
    }

    public EntryBuilder withAmount(String aAmount){
        amount = aAmount;
        return this;
    }

    public EntryBuilder withMemo(String aMemo){
        memo = aMemo;
        return this;
    }

    public EntryBuilder withTag(String aTagName){
        Tag tag = new Tag(aTagName);
        tags.add(tag);
        return this;
    }

    public EntryBuilder withCreatedAt(LocalDateTime aCreatedAt){
        createdAt = DateUtil.asDate(aCreatedAt);
        return this;
    }

    public Entry build(){
        Entry entry = new Entry();
        entry.setAmount(amount);
        entry.setMemo(memo);
        entry.setHash(HashUtil.getUniueHash());
        entry.setCreated_at(getCreatedAt());
        entry.setTags(tags);
        return entry;
    }

    private void assertNotNull(String aValue) {
        if(aValue == null){
            throw new IllegalArgumentException();
        }
    }

    private Date getCreatedAt(){
        if(createdAt == null){
            createdAt = new Date();
        }
        return createdAt;
    }


}
