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

    public void persistTag(Tag aTag){
        tagDao.save(aTag);
    }

    public Set<Tag> getTags(Entry aEntry) {
        return tagDao.getTags(aEntry);
    }

    public Set<Tag> getTags(AppUser aUser) {
        return tagDao.getAllByUser(aUser);
    }

}
