package de.pfann.budgetmanager.server.core.rotationjobs;

import de.pfann.budgetmanager.server.core.persistens.core.AbstractDao;
import de.pfann.budgetmanager.server.core.persistens.core.DbReader;
import de.pfann.budgetmanager.server.core.persistens.core.DbWriter;

import java.util.Collections;
import java.util.List;

public class RunDao extends AbstractDao {

    public static RunDao create(){
        return new RunDao(DbWriter.create(),DbReader.create());
    }

    protected RunDao(DbWriter _dbWriter, DbReader _dbReader) {
        super(_dbWriter, _dbReader);
    }

    @Override
    protected Class<?> getEntityClass() {
        return Run.class;
    }

    public List<Run> getYoungesRun(){
        List<Run> runs = (List<Run>) doGetAll();
        Collections.sort(runs);
        return runs;

    }

    public List<Run> getAll(){
        return (List<Run>) doGetAll();
    }

}
