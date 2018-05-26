package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.persistens.core.AbstractDao;
import de.pfann.budgetmanager.server.persistens.core.DbReader;
import de.pfann.budgetmanager.server.persistens.core.DbWriter;
import de.pfann.budgetmanager.server.persistens.model.AppUser;
import de.pfann.budgetmanager.server.persistens.model.TagStatistic;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class TagStatisticDao extends AbstractDao {


    public static TagStatisticDao create(){
        return new TagStatisticDao(DbWriter.create(),DbReader.create());
    }

    private TagStatisticDao(DbWriter _dbWriter, DbReader _dbReader) {
        super(_dbWriter, _dbReader);
    }

    @Override
    protected Class<?> getEntityClass() {
        return TagStatistic.class;
    }

    public List<TagStatistic> getAllByUser(AppUser aUser){
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("user", aUser));
        return new ArrayList<>((List<TagStatistic>) doGet(criteria));
    }

    public void remove(TagStatistic aTagStatistic){
        delete(aTagStatistic);
    }
}
