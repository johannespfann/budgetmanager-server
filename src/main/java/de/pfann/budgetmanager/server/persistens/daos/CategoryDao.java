package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.persistens.core.AbstractDao;
import de.pfann.budgetmanager.server.persistens.core.DbReader;
import de.pfann.budgetmanager.server.persistens.core.DbWriter;
import de.pfann.budgetmanager.server.util.Util;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CategoryDao extends AbstractDao {

    public static CategoryDao create(){
        return new CategoryDao(DbWriter.create(), DbReader.create());
    }

    protected CategoryDao(DbWriter _dbWriter, DbReader _dbReader) {
        super(_dbWriter, _dbReader);
    }

    @Override
    protected Class<?> getEntityClass() {
        return Category.class;
    }

    public List<Category> getAllByUser(AppUser aUser){
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("appUser", aUser));
        return (List<Category>) doGet(criteria);
    }

    public Category createDefaultCategory(){
        Category category = new Category();
        category.setName("Allgemein");
        category.setHash(Util.getUniueHash(10000000,999999999));
        category = (Category) save(category);
        return category;
    }

    public List<Category> getCategory(String aHash) {
        DetachedCriteria criteria = getCriteria();
        criteria.add(Restrictions.eq("hash", aHash));
        return (List<Category>) doGet(criteria);
    }
}
