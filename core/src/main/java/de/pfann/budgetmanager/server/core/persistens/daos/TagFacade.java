package de.pfann.budgetmanager.server.core.persistens.daos;

import de.pfann.budgetmanager.server.core.model.AppUser;
import de.pfann.budgetmanager.server.core.model.Entry;
import de.pfann.budgetmanager.server.core.model.Tag;

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
