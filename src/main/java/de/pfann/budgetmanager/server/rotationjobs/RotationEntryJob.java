package de.pfann.budgetmanager.server.rotationjobs;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.daos.EntryFacade;
import de.pfann.budgetmanager.server.util.LogUtil;

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

        Examiner examiner = Examiner.builder()
                .withPattern(patterns)
                .forDate(aRun.getExecuted_at())
                .build();

        List<AppUser> users = userFacade.getAllUser();

        for(AppUser user : users){
            LogUtil.info(this.getClass(),"for user: " + user.getName());
            List<RotationEntry> rotationEntries = rotationEntryFacade.getRotationEntries(user);

            for(RotationEntry rotationEntry : rotationEntries){

                if(examiner.executeable(rotationEntry)){
                    LogUtil.info(this.getClass(),"generate entry");
                    Entry entry = EntryTransformer.builder()
                            .forDate(new Date())
                            .build()
                            .createEntry(rotationEntry);

                    entryFacade.persistEntry(entry);
                }
            }
        }
    }

    @Override
    public void postExecution(Run aRun) {
        LogUtil.info(this.getClass(),"[Stop  Run]");
    }
}
