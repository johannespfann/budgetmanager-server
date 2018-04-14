package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.model.AppUser;
import de.pfann.budgetmanager.server.persistens.core.AbstractDao;
import de.pfann.budgetmanager.server.persistens.core.DbReader;
import de.pfann.budgetmanager.server.persistens.core.DbWriter;
import de.pfann.budgetmanager.server.persistens.model.RotationEntry;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class RotationEntryDao extends AbstractDao {

    public static RotationEntryDao create(){
        return new RotationEntryDao(DbWriter.create(),DbReader.create());
    }

    protected RotationEntryDao(DbWriter _dbWriter, DbReader _dbReader) {
        super(_dbWriter, _dbReader);
    }

    @Override
    protected Class<?> getEntityClass() {
        return RotationEntry.class;
    }

    public List<RotationEntry> getRotationEntries(AppUser aUser) {
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("user", aUser));
        return (List<RotationEntry>) doGet(criteria);
    }

    public RotationEntry getRotationEntryByHash(String aHash) {
        LogUtil.info(this.getClass(),"get RotationEntryByHash: " +aHash);
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("hash", aHash));
        LogUtil.info(this.getClass(),"found");
        return (RotationEntry) doGet(criteria).get(0);
    }
}