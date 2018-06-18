package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.TagStatistic;

import java.util.List;

public interface TagStatisticFacade {
    void delete(TagStatistic aTagStatistic);

    List<TagStatistic> getAllByUser(AppUser aUser);

    void persist(TagStatistic aTagStatistic);

    void persist(List<TagStatistic> aTagStatistics, AppUser aUser);
}
