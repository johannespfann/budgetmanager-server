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
        return runs.get(runs.size()-1);
    }

    @Override
    public List<Run> getAllRuns() {
        List<CDBRun> runs = cdbRunDao.getAll();
        List<Run> runList = new LinkedList<>();

        for(CDBRun run : runs){
            Run newRun = new Run(DateUtil.asLocalDateTime(run.getExecutedAt()));
            runList.add(newRun);
        }

        return runList;
    }
}
