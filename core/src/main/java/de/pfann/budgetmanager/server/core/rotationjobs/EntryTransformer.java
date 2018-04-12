package de.pfann.budgetmanager.server.core.rotationjobs;

import de.pfann.budgetmanager.server.common.util.Util;
import de.pfann.budgetmanager.server.persistens.model.Entry;
import de.pfann.budgetmanager.server.persistens.model.RotationEntry;
import de.pfann.budgetmanager.server.persistens.model.Tag;
import de.pfann.budgetmanager.server.persistens.model.TagTemplate;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntryTransformer {

    private static final String TAG_SEPERATOR = ";";

    private Date createdDate;

    private EntryTransformer(){
        createdDate = new Date();
    }

    private EntryTransformer(Date aDate){
        createdDate = aDate;
    }

    public Entry createEntry(RotationEntry aRotationEntry){
        Entry newEntry = new Entry();

        newEntry.setAppUser(aRotationEntry.getUser());
        newEntry.setAmount(aRotationEntry.getAmount());
        newEntry.setHash(Util.getUniueHash(10000000,999999999));
        newEntry.setMemo(aRotationEntry.getMemo());

        List<Tag> tags = new ArrayList<>();

        for(TagTemplate tagTemplate : aRotationEntry.getTags()){
            Tag tag = new Tag();
            tag.setAppUser(aRotationEntry.getUser());
            tag.setName(tagTemplate.getName());
            tags.add(tag);
        }

        newEntry.setTags(tags);
        newEntry.setCreated_at(new Date());

        return newEntry;
    }

    /**
     * transform tags seperated with ; into a list of tag-object
     * @param aTags
     * @return
     */
    private List<Tag> transformTags(String aTags){
        List<Tag> tags = new ArrayList<>();

        String[] values = new String[]{};

        if(aTags != null) {
            values = aTags.split(TAG_SEPERATOR);
        }
        for(String value : values){
            tags.add(new Tag(value));
        }

        return tags;
    }

    public static EntryTransformerBuilder builder(){
        return new EntryTransformerBuilder();
    }


    public static class EntryTransformerBuilder {

        private EntryTransformer entryTransformer;

        public EntryTransformerBuilder(){
            entryTransformer = new EntryTransformer();
        }

        public EntryTransformerBuilder forDate(Date aDate){
            entryTransformer = new EntryTransformer(aDate);
            return this;
        }

        public EntryTransformer build(){
            return entryTransformer;
        }

    }

}
