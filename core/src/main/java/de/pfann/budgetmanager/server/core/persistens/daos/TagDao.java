package de.pfann.budgetmanager.server.core.persistens.daos;

import de.pfann.budgetmanager.server.core.model.AppUser;
import de.pfann.budgetmanager.server.core.model.Entry;
import de.pfann.budgetmanager.server.core.model.Tag;
import de.pfann.budgetmanager.server.core.persistens.core.AbstractDao;
import de.pfann.budgetmanager.server.core.persistens.core.DbReader;
import de.pfann.budgetmanager.server.core.persistens.core.DbWriter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TagDao extends AbstractDao {

    protected TagDao(DbWriter _dbWriter, DbReader _dbReader) {
        super(_dbWriter, _dbReader);
    }

    @Override
    protected Class<?> getEntityClass() {
        return Tag.class;
    }

    public Set<Tag> getAllByUser(AppUser aUser){
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("appUser", aUser));
        return new HashSet<>((List<Tag>) doGet(criteria));
    }

    public static TagDao create() {
        return new TagDao(DbWriter.create(),DbReader.create());
    }

    public void deleteAllByUser(AppUser aUser){
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("appUser", aUser));
        List<Tag> tags = (List<Tag>) doGet(criteria);

        for(Tag tag : tags){
            delete(tag);
        }

    }

    public Tag getTag(AppUser aUser, String aName) {
        System.out.println("getTags ...");
        System.out.println("Name: " + aName);
        System.out.println("Id: " + aUser.getId());
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("name", aName));
        criteria.add(Restrictions.eq("appUser", aUser));

        List<Tag> tags = (List<Tag>) doGet(criteria);
        System.out.println("get Tag: " + tags.get(0).getName());
        return tags.get(0);
    }

    public Set<Tag> getTags(Entry aEntry) {
        DetachedCriteria criteria = getCriteria("tag");
        criteria.createAlias("tag.entries","entry");
        criteria.add(Restrictions.eq("entry.id", aEntry.getId()));
        return new HashSet<>((List<Tag>) doGet(criteria));
    }
}
