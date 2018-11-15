package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.RunFacade;
import de.pfann.budgetmanager.server.common.model.Run;
import de.pfann.budgetmanager.server.common.model.RunInfo;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBRunDao;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBRunDoaFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBRun;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBRunAction;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBRunId;
import de.pfann.server.logging.core.LogUtil;
import de.pfann.server.logging.core.RunLog;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CDBRunFacade implements RunFacade {

    private CDBRunDao cdbRunDao;

    public CDBRunFacade(CDBRunDoaFactory aCdbRunDoaFactory){
        cdbRunDao = aCdbRunDoaFactory.createDao();
    }

    @Override
    public void persist(Run aRun) {
        String runId = CDBRunId.createId(aRun.getExecuted_at());
        CDBRun run = new CDBRun(DateUtil.asDate(aRun.getExecuted_at()));
        run.setId(runId);

        RunLog.info(this.getClass(),"[Persist new Run]   : " + aRun.getExecuted_at());
        RunLog.info(this.getClass(),"- IncomingTime    : " + aRun.getExecuted_at());
        RunLog.info(this.getClass(),"- TransformedTime : " + run.getExecutedAt() + " with id: " + run.getId());
        cdbRunDao.add(run);
    }

    @Override
    public void persist(RunInfo aRunInfo) {
        String runId = CDBRunId.createId(aRunInfo.getRun().getExecuted_at());
        CDBRun cdbRun = cdbRunDao.get(runId);

        CDBRunAction action = new CDBRunAction(
                aRunInfo.getIdentifier(),
                aRunInfo.getState(),
                DateUtil.asDate(aRunInfo.getStart_at()),
                DateUtil.asDate(aRunInfo.getEnd_at()));

        cdbRun.addRunAction(action);
        cdbRunDao.update(cdbRun);
    }

    @Override
    public Run getLastRun() {
        // TODO geht besser
        List<Run> runs = getAllRuns();
        if(runs.size() == 0){
            return null;
        }
        Run run = findYoungestRun(runs);

        return run;
    }

    private Run findYoungestRun(List<Run> aRuns) {
        LocalDateTime youngestTime = DateUtil.asLocalDateTime(DateUtil.getMinimumDate());
        Run youngestRun = new Run(youngestTime);
        for(Run run : aRuns){
            if(youngestRun.getExecuted_at().isBefore(run.getExecuted_at())){
                youngestRun = run;
            }
        }
        return youngestRun;
    }

    @Override
    public List<Run> getAllRuns() {
        RunLog.info(this.getClass(),"Get all runs");
        List<CDBRun> runs = cdbRunDao.getAll();
        RunLog.info(this.getClass(),"Found " + runs.size() + " runs");
        List<Run> runList = new LinkedList<>();

        for(CDBRun run : runs){
            Run newRun = new Run(DateUtil.asLocalDateTime(run.getExecutedAt()));
            runList.add(newRun);
        }

        return runList;
    }
}
