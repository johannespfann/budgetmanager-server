package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.persistens.core.AbstractDao;
import de.pfann.budgetmanager.server.persistens.core.DbReader;
import de.pfann.budgetmanager.server.persistens.core.DbWriter;
import de.pfann.budgetmanager.server.persistens.model.RotationEntry;
import de.pfann.budgetmanager.server.persistens.model.TagTemplate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class TagTemplateDao extends AbstractDao {

    public static TagTemplateDao create(){
        return new TagTemplateDao(DbWriter.create(),DbReader.create());
    }

    protected TagTemplateDao(DbWriter _dbWriter, DbReader _dbReader) {
        super(_dbWriter, _dbReader);
    }

    @Override
    protected Class<?> getEntityClass() {
        return TagTemplate.class;
    }

    public List<TagTemplate> findAllByRotationEntry(RotationEntry aRotationEntry){
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("rotationEntry", aRotationEntry));
        return (List<TagTemplate>)doGet(criteria);
    }




}
