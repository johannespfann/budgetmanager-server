package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.TagStatisticFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.TagStatistic;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDao;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBTagStatistic;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBUser;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBUserId;
import org.ektorp.CouchDbInstance;
import org.ektorp.impl.ObjectMapperFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedList;
import java.util.List;

public class CDBStatisticFacade implements TagStatisticFacade {

    private CDBUserDao userDao;

    public CDBStatisticFacade(CDBUserDaoFactory aUserDaoFactory){
        userDao = aUserDaoFactory.createDao();
    }

    @Override
    public void delete(TagStatistic aTagStatistic) {
        throw new NotImplementedException();
    }

    @Override
    public List<TagStatistic> getAllByUser(AppUser aUser) {
        CDBUserId userId = CDBUserId.create(aUser.getName());
        CDBUser user = userDao.get(userId.toString());
        List<TagStatistic> tagStatistics = new LinkedList<>();

        for(CDBTagStatistic cdbTag : user.getTagStatistics()){
            TagStatistic tag = new TagStatistic();
            tag.setName(cdbTag.getName());
            tag.setWeight(cdbTag.getWeight());
            tagStatistics.add(tag);
        }

        return tagStatistics;
    }

    @Override
    public void persist(TagStatistic aTagStatistic) {
        AppUser appUser = aTagStatistic.getUser();
        CDBUserId userId = CDBUserId.create(appUser.getName());
        CDBUser user = userDao.get(userId.toString());

        CDBTagStatistic cdbTagStatistic = new CDBTagStatistic();
        cdbTagStatistic.setName(aTagStatistic.getName());
        cdbTagStatistic.setWeight(aTagStatistic.getWeight());
        user.getTagStatistics().add(cdbTagStatistic);

        userDao.update(user);
    }

    @Override
    public void persist(List<TagStatistic> aTagStatistics, AppUser aAppUser) {
        CDBUserId userId = CDBUserId.create(aAppUser.getName());
        CDBUser cdbUser = userDao.get(userId.toString());

        List<CDBTagStatistic> cdbTagStatistics = new LinkedList<>();

        for(TagStatistic tag : aTagStatistics){
            CDBTagStatistic cdbTagStatistic = new CDBTagStatistic();
            cdbTagStatistic.setName(tag.getName());
            cdbTagStatistic.setWeight(tag.getWeight());
            cdbTagStatistics.add(cdbTagStatistic);
        }

        cdbUser.setTagStatistics(cdbTagStatistics);
        userDao.update(cdbUser);
    }

}
