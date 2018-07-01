package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.TagStatisticFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.TagStatistic;
import org.ektorp.CouchDbInstance;
import org.ektorp.impl.ObjectMapperFactory;

import java.util.List;

public class CDBStatisticFacade implements TagStatisticFacade {

    private CouchDbInstance couchDbInstance;
    private ObjectMapperFactory objectMapperFactory;

    public CDBStatisticFacade(CouchDbInstance aCouchInstance, ObjectMapperFactory aObjectMapperFactory){
        couchDbInstance = aCouchInstance;
        objectMapperFactory = aObjectMapperFactory;
    }

    @Override
    public void delete(TagStatistic aTagStatistic) {

    }

    @Override
    public List<TagStatistic> getAllByUser(AppUser aUser) {
        return null;
    }

    @Override
    public void persist(TagStatistic aTagStatistic) {

    }

    @Override
    public void persist(List<TagStatistic> aTagStatistics, AppUser aUser) {

    }

}
