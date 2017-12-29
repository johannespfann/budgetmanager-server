package de.pfann.budgetmanager.server.persistens;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Tag;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.LinkedList;
import java.util.List;

public class TagDao extends AbstractDao{

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
        List<Tag> tags = (List<Tag>) doGet(criteria);

        for(Tag tag : tags){
            delete(tag);
        }
    }
}
