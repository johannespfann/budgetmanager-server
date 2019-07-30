package de.pfann.budgetmanager.server.jobengine.rotationjobs;

import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.facade.EntryFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.StandingOrder;
import de.pfann.budgetmanager.server.common.model.Run;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.jobengine.core.Job;
import de.pfann.server.logging.core.RunLog;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class RotationEntryJob implements Job {

    private List<RotationEntryPattern> patterns;

    private EntryFacade entryFacade;

    private AppUserFacade userFacade;

    private StandingOrderFacade rotationEntryFacade;


    public RotationEntryJob(List<RotationEntryPattern> aPatterns, AppUserFacade aUserFacade, EntryFacade aEntryFacade, StandingOrderFacade aRotationEntryFacade){
        patterns = aPatterns;
        entryFacade = aEntryFacade;
        userFacade = aUserFacade;
        rotationEntryFacade = aRotationEntryFacade;
    }

    @Override
    public String getIdentifier() {
        return RotationEntryJob.class.getSimpleName();
    }

    @Override
    public void preExecution(Run aRun) {
        RunLog.info(this.getClass(),"[Start Run]");
        RunLog.info(this.getClass(),"Jobname: " + getIdentifier());
        RunLog.info(this.getClass(),"with " + patterns.size() + " pattern");
    }

    @Override
    public void execute(Run aRun) {

        List<AppUser> users = userFacade.getAllUser();

        RunLog.info(this.getClass(),"RotationJobs are for " + users.size() + " users");

        for(AppUser user: users){
            RunLog.info(this.getClass(),"Execute for user: " + user.getName());
            List<StandingOrder> rotationEntries = rotationEntryFacade.getRotationEntries(user);

            RunLog.info(this.getClass(),"with : " + rotationEntries.size() + " rotationentries");

            for(StandingOrder rotationEntry : rotationEntries){
                executeRotationEntry(aRun,rotationEntry);
            }
        }
    }

    private void executeRotationEntry(Run currentRun,StandingOrder rotationEntry){
        RunLog.info(this.getClass(),"-----> Durchsuche  " + rotationEntry.getHash());
        for(RotationEntryPattern pattern : patterns){

            RunLog.info(this.getClass(),"with Pattern " + pattern.getClass().getSimpleName());
            if(isExecuteable(currentRun, rotationEntry, pattern)) {
                LocalDateTime currentDate = currentRun.getExecuted_at();
                LocalDateTime startDate = DateUtil.asLocalDateTime(rotationEntry.getStart_at());
                Date executionDate = DateUtil.asDate(pattern.getExecutionDate(startDate, currentDate));

                Entry entry = EntryTransformer.builder()
                        .forDate(executionDate)
                        .build()
                        .createEntry(rotationEntry);
                entryFacade.persistEntry(entry);
                rotationEntry.setLast_executed(executionDate);
                rotationEntryFacade.update(rotationEntry);
                RunLog.info(this.getClass(),"Persist new entry + " + entry.getHash());
            }
        }

    }

    private boolean isExecuteable(Run currentRun, StandingOrder rotationEntry, RotationEntryPattern pattern) {
        if(!pattern.isValidPattern(rotationEntry)){
            return false;
        }

        if(pattern.isExecutable(currentRun.getExecuted_at(),rotationEntry)){
            RunLog.info(this.getClass(),"isExecutable -> return true");
           return true;
        }

        return false;
    }

    @Override
    public void postExecution(Run aRun) {
        RunLog.info(this.getClass(),"[Stop  Run]");
    }
}
