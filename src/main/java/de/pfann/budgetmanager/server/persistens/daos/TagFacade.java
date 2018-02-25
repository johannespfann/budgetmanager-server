package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.model.Tag;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TagFacade {

    private TagDao tagDao;

    public TagFacade(){
        tagDao = TagDao.create();
    }

    public void updateTagsWithUser(AppUser aAppUser, List<Tag> aEntryTags) {
        List<Tag> persistedTags = tagDao.getAllByUser(aAppUser);
        for(Tag tag : aEntryTags){
            if(!alreadyExists(tag,persistedTags)){
                tagDao.save(tag);
            }
        }
    }

    public void persistTag(Tag aTag){
        tagDao.save(aTag);
    }


    private boolean alreadyExists(Tag aTag, List<Tag> aTags){
        for(Tag tag : aTags){
            if(tag.getName() == aTag.getName()){
                return true;
            }
        }
        return false;
    }

    public List<Tag> getPersistedTagObjects(AppUser aUser, List<Tag> aTags) {
        List<Tag> persistedTags = new LinkedList<>();

        aTags.forEach(tag -> {
            persistedTags.add(tagDao.getTag(aUser, tag.getName()));
        });

        return persistedTags;
    }

    public Set<Tag> getTags(Entry aEntry) {
        return tagDao.getTags(aEntry);
    }
}
