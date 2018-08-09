package de.pfann.budgetmanager.server.jobengine.rotationjobs;

import de.pfann.budgetmanager.server.common.facade.EntryFacade;
import de.pfann.budgetmanager.server.common.facade.StandingOrderFacade;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.StandingOrder;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.daos.EntrySQLFacade;
import de.pfann.budgetmanager.server.persistens.daos.RotationEntrySQLFacade;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class RotationEntryExecuter {

    private List<RotationEntryPattern> patterns;
    private StandingOrderFacade rotationEntryFacade;
    private EntryFacade entryFacade;

    public RotationEntryExecuter(List<RotationEntryPattern> aPatterns, StandingOrderFacade aRotationEntryFacade, EntryFacade aEntryFacade){
        patterns = aPatterns;
        rotationEntryFacade = aRotationEntryFacade;
        entryFacade = aEntryFacade;
    }


    public void executeRotationEntry(LocalDateTime executionDateTime, StandingOrder aRotationEntry){
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
                rotationEntryFacade.update(aRotationEntry);
            }
        }
    }



    private boolean isExecuteable(LocalDateTime currentRun, StandingOrder rotationEntry, RotationEntryPattern pattern) {
        if(!pattern.isValidPattern(rotationEntry)){
            return false;
        }

        if(pattern.isExecutable(currentRun,rotationEntry)){
            return true;
        }
        return false;
    }



}
