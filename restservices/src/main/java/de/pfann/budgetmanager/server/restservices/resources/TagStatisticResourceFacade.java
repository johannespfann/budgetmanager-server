package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.facade.TagStatisticFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.TagStatistic;

import java.util.List;

public class TagStatisticResourceFacade {

    private final TagStatisticFacade tagStatisticFacade;
    private final AppUserFacade userFacade;

    public TagStatisticResourceFacade(TagStatisticFacade aTagStatisitcFacade, AppUserFacade aAppUserFacade){
        tagStatisticFacade = aTagStatisitcFacade;
        userFacade = aAppUserFacade;
    }

    public List<TagStatistic> getAllTagStatistics(String aUser){
        try {
            AppUser user = userFacade.getUserByNameOrEmail(aUser);
            return tagStatisticFacade.getAllByUser(user);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public void persistStatistics(String aOwner, List<TagStatistic> aStatistics){
        try{
            AppUser user = userFacade.getUserByNameOrEmail(aOwner);
            tagStatisticFacade.persist(aStatistics,user);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }

    }

}
