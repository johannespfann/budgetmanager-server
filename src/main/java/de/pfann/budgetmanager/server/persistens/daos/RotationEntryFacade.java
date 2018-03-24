package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.RotationEntry;

import java.util.List;

public class RotationEntryFacade {

    private RotationEntryDao roationEntryDao;

    public RotationEntryFacade(){
        roationEntryDao = RotationEntryDao.create();
    }

    public RotationEntryFacade(RotationEntryDao aDao){
        roationEntryDao = aDao;
    }

    public void save(RotationEntry aEntry){
        roationEntryDao.save(aEntry);
    }

    public List<RotationEntry> getRotationEntries(AppUser aUser) {
        return roationEntryDao.getRotationEntries(aUser);
    }
}
