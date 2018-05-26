package de.pfann.budgetmanager.server.core.rotationjobs;

import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.core.jobengine.Job;
import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.daos.EntryFacade;
import de.pfann.budgetmanager.server.persistens.daos.RotationEntryFacade;
import de.pfann.budgetmanager.server.persistens.model.AppUser;
import de.pfann.budgetmanager.server.persistens.model.Entry;
import de.pfann.budgetmanager.server.persistens.model.RotationEntry;
import de.pfann.budgetmanager.server.persistens.model.Run;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class RotationEntryJob implements Job {

    private List<RotationEntryPattern> patterns;

    private EntryFacade entryFacade;

    private AppUserFacade userFacade;

    private RotationEntryFacade rotationEntryFacade;


    public RotationEntryJob(List<RotationEntryPattern> aPatterns, AppUserFacade aUserFacade, EntryFacade aEntryFacade, RotationEntryFacade aRotationEntryFacade){
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

        for(AppUser user: users){
            LogUtil.info(this.getClass(),"Execute for user: " + user.getName());
            List<RotationEntry> rotationEntries = rotationEntryFacade.getRotationEntries(user);

            for(RotationEntry rotationEntry : rotationEntries){
                executeRotationEntry(aRun,rotationEntry);
            }
        }
    }

    private void executeRotationEntry(Run currentRun,RotationEntry rotationEntry){

        for(RotationEntryPattern pattern : patterns){

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
            return false;
        }

        if(pattern.isExecutable(currentRun.getExecuted_at(),rotationEntry)){
           return true;
        }

        return false;
    }


    @Override
    public void postExecution(Run aRun) {
        LogUtil.info(this.getClass(),"[Stop  Run]");
    }
}
