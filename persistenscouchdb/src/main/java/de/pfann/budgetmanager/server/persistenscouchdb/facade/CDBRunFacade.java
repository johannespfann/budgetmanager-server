package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.RunFacade;
import de.pfann.budgetmanager.server.common.model.Run;
import de.pfann.budgetmanager.server.common.model.RunInfo;
import org.ektorp.CouchDbInstance;
import org.ektorp.impl.ObjectMapperFactory;

import java.util.List;

public class CDBRunFacade implements RunFacade {

    private CouchDbInstance couchDbInstance;
    private ObjectMapperFactory objectMapperFactory;

    public CDBRunFacade(CouchDbInstance aCouchInstance, ObjectMapperFactory aObjectMapperFactory){
        couchDbInstance = aCouchInstance;
        objectMapperFactory = aObjectMapperFactory;
    }

    @Override
    public void persist(Run aRun) {

    }

    @Override
    public void persist(RunInfo aRunInfo) {

    }

    @Override
    public Run getLastRun() {
        return null;
    }

    @Override
    public List<Run> getAllRuns() {
        return null;
    }
}
