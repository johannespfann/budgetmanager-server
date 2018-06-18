package de.pfann.budgetmanager.server.persistens.daos;


import de.pfann.budgetmanager.server.common.facade.RunFacade;
import de.pfann.budgetmanager.server.common.model.Run;
import de.pfann.budgetmanager.server.common.model.RunInfo;

import java.util.List;

public class RunSQLFacade implements RunFacade {

    private RunDao runDao;

    private RunInfoDao runInfoDao;

    public RunSQLFacade(){
        runDao = RunDao.create();
        runInfoDao = RunInfoDao.create();
    }

    public RunSQLFacade(RunInfoDao aRunInfoDao, RunDao aRunDao){
        runDao = aRunDao;
        runInfoDao = aRunInfoDao;
    }

    @Override
    public void persist(Run aRun){
        runDao.save(aRun);
    }

    @Override
    public void persist(RunInfo aRunInfo){
        runInfoDao.save(aRunInfo);
    }

    @Override
    public Run getLastRun() {
        return  runDao.getYoungesRun();
    }

    @Override
    public List<Run> getAllRuns() {
        return runDao.getAll();
    }
}
