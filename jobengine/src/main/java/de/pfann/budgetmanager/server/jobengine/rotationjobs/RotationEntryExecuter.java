package de.pfann.budgetmanager.server.jobengine.rotationjobs;

import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.daos.EntryFacade;
import de.pfann.budgetmanager.server.persistens.daos.RotationEntryFacade;
import de.pfann.budgetmanager.server.persistens.model.Entry;
import de.pfann.budgetmanager.server.persistens.model.RotationEntry;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class RotationEntryExecuter {

    private List<RotationEntryPattern> patterns;
    private RotationEntryFacade rotationEntryFacade;
    private EntryFacade entryFacade;

    public RotationEntryExecuter(List<RotationEntryPattern> aPatterns, RotationEntryFacade aRotationEntryFacade, EntryFacade aEntryFacade){
        patterns = aPatterns;
        rotationEntryFacade = aRotationEntryFacade;
        entryFacade = aEntryFacade;
    }


    private void executeRotationEntry(LocalDateTime executionDateTime, RotationEntry aRotationEntry){
        LogUtil.info(this.getClass(),"-----> Durchsuche  " + aRotationEntry.getHash());
        for(RotationEntryPattern pattern : patterns){

            LogUtil.info(this.getClass(),"with Pattern " + pattern.getClass());
            if(isExecuteable(executionDateTime, aRotationEntry, pattern)) {
                LocalDateTime currentDate = executionDateTime;
                LocalDateTime startDate = DateUtil.asLocalDateTime(aRotationEntry.getStart_at());
                Date executionDate = DateUtil.asDate(pattern.getExecutionDate(startDate, currentDate));

                Entry entry = EntryTransformer.builder()
                        .forDate(executionDate)
                        .build()
                        .createEntry(aRotationEntry);
                entryFacade.persistEntry(entry);
                aRotationEntry.setLast_executed(executionDate);
                rotationEntryFacade.save(aRotationEntry);
            }
        }
    }



    private boolean isExecuteable(LocalDateTime currentRun, RotationEntry rotationEntry, RotationEntryPattern pattern) {
        if(!pattern.isValidPattern(rotationEntry)){
            return false;
        }

        if(pattern.isExecutable(currentRun,rotationEntry)){
            return true;
        }
        return false;
    }



}
