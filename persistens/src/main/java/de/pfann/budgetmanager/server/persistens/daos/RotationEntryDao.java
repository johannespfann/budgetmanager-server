package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.StandingOrder;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.core.AbstractDao;
import de.pfann.budgetmanager.server.persistens.core.DbReader;
import de.pfann.budgetmanager.server.persistens.core.DbWriter;
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
        return StandingOrder.class;
    }

    public List<StandingOrder> getRotationEntries(AppUser aUser) {
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("user", aUser));
        return (List<StandingOrder>) doGet(criteria);
    }

    public StandingOrder getRotationEntryByHash(String aHash) {
        LogUtil.info(this.getClass(),"get RotationEntryByHash: " +aHash);
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("hash", aHash));
        LogUtil.info(this.getClass(),"found");
        StandingOrder entry = (StandingOrder) doGet(criteria).get(0);
        LogUtil.info(this.getClass(),entry.getHash());
        return entry;
    }
}
