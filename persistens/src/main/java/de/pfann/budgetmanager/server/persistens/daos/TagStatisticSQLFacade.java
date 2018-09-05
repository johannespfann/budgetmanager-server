package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.common.facade.TagStatisticFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.TagStatistic;
import de.pfann.server.logging.core.LogUtil;


import java.util.List;

public class TagStatisticSQLFacade implements TagStatisticFacade {

    private AppUserDao userDao;

    private TagStatisticDao tagStatisticDao;

    public TagStatisticSQLFacade(){
        userDao = AppUserDao.create();
        tagStatisticDao = TagStatisticDao.create();
    }

    @Override
    public void delete(TagStatistic aTagStatistic){
        tagStatisticDao.delete(aTagStatistic);
    }

    @Override
    public List<TagStatistic> getAllByUser(AppUser aUser){
        return tagStatisticDao.getAllByUser(aUser);
    }

    @Override
    public void persist(TagStatistic aTagStatistic) {
        tagStatisticDao.save(tagStatisticDao);
    }

    @Override
    public void persist(List<TagStatistic> aTagStatistics, AppUser aUser) {

        AppUser user = aUser;
        user = getUser(aUser, user);

        // delete old statistic
        List<TagStatistic> persistedTagStatistics = tagStatisticDao.getAllByUser(user);
        for (TagStatistic tag : persistedTagStatistics) {
            tagStatisticDao.remove(tag);
        }

        // persist new statistic
        for (TagStatistic tag : aTagStatistics) {
            tag.setUser(user);
            tagStatisticDao.save(tag);
        }
    }

    private AppUser getUser(AppUser aUser, AppUser user) {
        try {
            user = userDao.getUserByNameOrEmail(aUser.getEmail());
        } catch (NoUserFoundException e) {
            LogUtil.error(this.getClass(),"Could not found user!");
        }
        return user;
    }
}
