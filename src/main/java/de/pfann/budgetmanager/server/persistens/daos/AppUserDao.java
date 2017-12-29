package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.persistens.core.AbstractDao;
import de.pfann.budgetmanager.server.persistens.core.DbReader;
import de.pfann.budgetmanager.server.persistens.core.DbWriter;

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


}
