package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.model.RotationEntry;

public class RotationEntryFacade {

    private RotationEntryDao roationEntryDao;

    public RotationEntryFacade(RotationEntryDao aDao){
        roationEntryDao = aDao;
    }

    public void save(RotationEntry aEntry){
        roationEntryDao.save(aEntry);
    }

}
