package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.persistens.model.AppUser;
import de.pfann.budgetmanager.server.persistens.model.Entry;
import de.pfann.budgetmanager.server.persistens.model.Tag;
import de.pfann.budgetmanager.server.persistens.core.AbstractDao;
import de.pfann.budgetmanager.server.persistens.core.DbReader;
import de.pfann.budgetmanager.server.persistens.core.DbWriter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class EntryDao extends AbstractDao {

    public static EntryDao create() {
        return new EntryDao(DbWriter.create(), DbReader.create());
    }

    protected EntryDao(DbWriter _dbWriter, DbReader _dbReader) {
        super(_dbWriter, _dbReader);
    }

    @Override
    protected Class<?> getEntityClass() {
        return Entry.class;
    }

    public List<Entry> getAll() {
        return (List<Entry>) doGetAll();
    }

    public List<Entry> getAllByUser(AppUser aUser) {
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("appUser", aUser));
        return (List<Entry>) doGet(criteria);
    }

    public List<Entry> getEntryByHash(String aHash){
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("hash", aHash));
        return (List<Entry>) doGet(criteria);
    }

    //https://stackoverflow.com/questions/18339421/query-many-to-many-relationship-with-detachedcriteria
    public List<Entry> getAllByTag(Tag aTag) {
        DetachedCriteria criteria = getCriteria("entry");

        criteria.createAlias("entry.tags","tag");
        criteria.add(Restrictions.eq("tag.id", aTag.getId()));
        //criteria.setProjection(Projections.distinct(Projections.property("id")));
        return (List<Entry>) doGet(criteria);

    }
}