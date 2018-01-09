package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.persistens.core.AbstractDao;
import de.pfann.budgetmanager.server.persistens.core.DbReader;
import de.pfann.budgetmanager.server.persistens.core.DbWriter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class AppUserDao extends AbstractDao {

    public static AppUserDao create() {
        return new AppUserDao(DbWriter.create(), DbReader.create());
    }

    protected AppUserDao(DbWriter _dbWriter, DbReader _dbReader) {
        super(_dbWriter, _dbReader);
    }

    @Override
    protected Class<?> getEntityClass() {
        return AppUser.class;
    }

    public AppUser getUser(String aUserName){
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("name", aUserName));
        return (AppUser) doGet(criteria).get(0);
    }

    public List<AppUser> getAll(){
        return (List<AppUser>)doGetAll();
    }
}
