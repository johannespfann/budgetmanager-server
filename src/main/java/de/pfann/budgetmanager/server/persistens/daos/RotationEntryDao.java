package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.model.RotationEntry;
import de.pfann.budgetmanager.server.persistens.core.AbstractDao;
import de.pfann.budgetmanager.server.persistens.core.DbReader;
import de.pfann.budgetmanager.server.persistens.core.DbWriter;

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

}
