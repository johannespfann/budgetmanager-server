package de.pfann.budgetmanager.server.persistens.daos;


import de.pfann.budgetmanager.server.common.facade.TagFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.Tag;

import java.util.Set;

public class TagSQLFacade implements TagFacade {

    private TagDao tagDao;

    public TagSQLFacade(){
        tagDao = TagDao.create();
    }

    @Override
    public void persistTag(Tag aTag){
        tagDao.save(aTag);
    }

    @Override
    public Set<Tag> getTags(Entry aEntry) {
        return tagDao.getTags(aEntry);
    }

    @Override
    public Set<Tag> getTags(AppUser aUser) {
        return tagDao.getAllByUser(aUser);
    }

}
