package de.pfann.budgetmanager.server.persistens;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Entry;
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

}
