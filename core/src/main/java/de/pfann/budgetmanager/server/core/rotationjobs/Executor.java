package de.pfann.budgetmanager.server.core.rotationjobs;

import de.pfann.budgetmanager.server.core.model.AppUser;
import de.pfann.budgetmanager.server.core.model.Entry;
import de.pfann.budgetmanager.server.core.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.core.persistens.daos.EntryFacade;

import java.util.Date;
import java.util.List;

public class Executor {

    private Examiner examiner;

    private EntryFacade entryFacade;

    private AppUserFacade userFacade;

    private RotationEntryFacade rotationEntryFacade;


    public Executor(Examiner aExaminer, AppUserFacade aUserFacade, EntryFacade aEntryFacade, RotationEntryFacade aRotationEntryFacade){
        examiner = aExaminer;
        entryFacade = aEntryFacade;
        rotationEntryFacade = aRotationEntryFacade;
    }

    public void execute(){

        // nehme jeden user und führe durch
        List<AppUser> users = userFacade.getAllUser();

        for(AppUser user : users){

            List<RotationEntry> rotationEntries = rotationEntryFacade.getRotationEntries(user);

            for(RotationEntry rotationEntry : rotationEntries){

                if(examiner.executeable(rotationEntry)){
                    Entry entry = EntryTransformer.builder()
                            .forDate(new Date())
                            .build()
                            .createEntry(rotationEntry);


                    entryFacade.persistEntry(entry);
                }

            }


        }

    }
}
