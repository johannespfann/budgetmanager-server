package de.pfann.budgetmanager.server.persistens;

import de.pfann.budgetmanager.server.model.AppUser;

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
