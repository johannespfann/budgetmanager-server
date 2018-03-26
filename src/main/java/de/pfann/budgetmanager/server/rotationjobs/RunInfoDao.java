package de.pfann.budgetmanager.server.rotationjobs;

import de.pfann.budgetmanager.server.persistens.core.AbstractDao;
import de.pfann.budgetmanager.server.persistens.core.DbReader;
import de.pfann.budgetmanager.server.persistens.core.DbWriter;

public class RunInfoDao extends AbstractDao {

    public static RunInfoDao create(){
        return new RunInfoDao(DbWriter.create(),DbReader.create());
    }

    protected RunInfoDao(DbWriter _dbWriter, DbReader _dbReader) {
        super(_dbWriter, _dbReader);
    }

    @Override
    protected Class<?> getEntityClass() {
        return null;
    }

}
