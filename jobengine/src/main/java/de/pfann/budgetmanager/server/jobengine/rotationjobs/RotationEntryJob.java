package de.pfann.budgetmanager.server.jobengine.rotationjobs;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.RotationEntry;
import de.pfann.budgetmanager.server.common.model.Run;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.jobengine.core.Job;
import de.pfann.budgetmanager.server.persistens.daos.AppUserSQLFacade;
import de.pfann.budgetmanager.server.persistens.daos.EntrySQLFacade;
import de.pfann.budgetmanager.server.persistens.daos.RotationEntrySQLFacade;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class RotationEntryJob implements Job {

    private List<RotationEntryPattern> patterns;

    private EntrySQLFacade entryFacade;

    private AppUserSQLFacade userFacade;

    private RotationEntrySQLFacade rotationEntryFacade;


    public RotationEntryJob(List<RotationEntryPattern> aPatterns, AppUserSQLFacade aUserFacade, EntrySQLFacade aEntryFacade, RotationEntrySQLFacade aRotationEntryFacade){
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
        LogUtil.info(this.getClass(),"[Start Run]");
        LogUtil.info(this.getClass(),"Jobname: " + getIdentifier());
        LogUtil.info(this.getClass(),"with " + patterns.size() + " pattern");
    }

    @Override
    public void execute(Run aRun) {

        List<AppUser> users = userFacade.getAllUser();

        LogUtil.info(this.getClass(),"RotationJobs are for " + users.size() + " users");

        for(AppUser user: users){
            LogUtil.info(this.getClass(),"Execute for user: " + user.getName());
            List<RotationEntry> rotationEntries = rotationEntryFacade.getRotationEntries(user);

            LogUtil.info(this.getClass(),"with : " + rotationEntries.size() + " rotationentries");

            for(RotationEntry rotationEntry : rotationEntries){
                executeRotationEntry(aRun,rotationEntry);
            }
        }
    }

    private void executeRotationEntry(Run currentRun,RotationEntry rotationEntry){
        LogUtil.info(this.getClass(),"-----> Durchsuche  " + rotationEntry.getHash());
        for(RotationEntryPattern pattern : patterns){

            LogUtil.info(this.getClass(),"with Pattern " + pattern.getClass());
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
                rotationEntryFacade.save(rotationEntry);
            }
        }

    }

    private boolean isExecuteable(Run currentRun, RotationEntry rotationEntry, RotationEntryPattern pattern) {
        if(!pattern.isValidPattern(rotationEntry)){
            LogUtil.info(this.getClass(),"isExecutable -> return false");
            return false;
        }

        if(pattern.isExecutable(currentRun.getExecuted_at(),rotationEntry)){
            LogUtil.info(this.getClass(),"isExecutable -> return true");
           return true;
        }
        LogUtil.info(this.getClass(),"isExecutable -> return false");
        return false;
    }


    @Override
    public void postExecution(Run aRun) {
        LogUtil.info(this.getClass(),"[Stop  Run]");
    }
}
