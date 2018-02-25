package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.model.Tag;
import de.pfann.budgetmanager.server.persistens.core.AbstractDao;
import de.pfann.budgetmanager.server.persistens.core.DbReader;
import de.pfann.budgetmanager.server.persistens.core.DbWriter;
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

    public List<Tag> getAllByUser(AppUser aUser){
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("appUser", aUser));
        return (List<Tag>) doGet(criteria);
    }

    public static TagDao create() {
        return new TagDao(DbWriter.create(),DbReader.create());
    }

    public void deleteAllByUser(AppUser aUser){
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("appUser", aUser));
        Set<Tag> tags = (Set<Tag>) doGet(criteria);

        for(Tag tag : tags){
            delete(tag);
        }
    }

    public Tag getTag(AppUser aUser, String aName) {
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("name", aName));
        criteria.add(Restrictions.eq("appUser", aUser.getId()));
        List<Tag> tags = (List<Tag>) doGet(criteria);
        return tags.get(0);
    }

    public Set<Tag> getTags(Entry aEntry) {
        DetachedCriteria criteria = getCriteria("tag");
        criteria.createAlias("tag.entries","entry");
        criteria.add(Restrictions.eq("entry.id", aEntry.getId()));
        return new HashSet<>((List<Tag>) doGet(criteria));
    }
}
