package de.pfann.budgetmanager.server.rotationjobs;

public class RunFacade {

    private RunDao runDao;

    private RunInfoDao runInfoDao;

    public RunFacade(){
        runDao = RunDao.create();
        runInfoDao = RunInfoDao.create();
    }

    public RunFacade(RunInfoDao aRunInfoDao, RunDao aRunDao){
        runDao = aRunDao;
        runInfoDao = aRunInfoDao;
    }

    public void persist(Run aRun){
        runDao.save(aRun);
    }

    public void persist(RunInfo aRunInfo){
        runInfoDao.save(aRunInfo);
    }

    public Run getLastRun() {
        return runDao.getYoungesRun();
    }


}
