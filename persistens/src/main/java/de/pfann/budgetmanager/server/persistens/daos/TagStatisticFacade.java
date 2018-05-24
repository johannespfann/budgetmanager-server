package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.persistens.model.AppUser;
import de.pfann.budgetmanager.server.persistens.model.TagStatistic;

import java.util.List;

public class TagStatisticFacade {

    private AppUserDao userDao;

    private TagStatisticDao tagStatisticDao;

    public TagStatisticFacade(){
        userDao = AppUserDao.create();
        tagStatisticDao = TagStatisticDao.create();
    }

    public void persist(TagStatistic aTagStatistic){
        tagStatisticDao.save(tagStatisticDao);
    }

    public void delete(TagStatistic aTagStatistic){
        tagStatisticDao.delete(aTagStatistic);
    }

    public List<TagStatistic> getAllByUser(AppUser aUser){
        return tagStatisticDao.getAllByUser(aUser);
    }

}
