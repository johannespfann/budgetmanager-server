package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.persistens.core.AbstractDao;
import de.pfann.budgetmanager.server.persistens.core.DbReader;
import de.pfann.budgetmanager.server.persistens.core.DbWriter;
import de.pfann.budgetmanager.server.persistens.model.Run;

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

    public Run getYoungesRun(){
        List<Run> runs = (List<Run>) doGetAll();

        if(runs.size() <= 0){
            return null;
        }

        Collections.sort(runs);
        return runs.get(0);
    }

    public List<Run> getAll(){
        return (List<Run>) doGetAll();
    }

}
