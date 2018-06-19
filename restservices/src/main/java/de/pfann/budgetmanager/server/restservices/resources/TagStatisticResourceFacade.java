package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.TagStatistic;
import de.pfann.budgetmanager.server.persistens.daos.AppUserSQLFacade;
import de.pfann.budgetmanager.server.persistens.daos.TagStatisticSQLFacade;

import java.util.List;

public class TagStatisticResourceFacade {

    private final TagStatisticSQLFacade tagStatisticFacade;
    private final AppUserSQLFacade userFacade;

    public TagStatisticResourceFacade(){
        tagStatisticFacade = new TagStatisticSQLFacade();
        userFacade = new AppUserSQLFacade();
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
