package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.persistens.daos.AppUserDao;
import de.pfann.budgetmanager.server.persistens.daos.CategoryDao;
import de.pfann.budgetmanager.server.persistens.daos.EntryDao;

import java.util.List;

public class CategoryFacade {

    private CategoryDao categoryDao;

    private EntryDao entryDao;

    public CategoryFacade(){
        categoryDao = CategoryDao.create();
        entryDao = EntryDao.create();
    }

    public CategoryFacade(CategoryDao aCategoryDao, EntryDao aEntryDao){
        categoryDao = aCategoryDao;
        entryDao = aEntryDao;
    }


    public List<Category> getAllByUser(AppUser aUser){
        return categoryDao.getAllByUser(aUser);
    }


    public Category getCategory(String aHash) {
        List<Category> categories = categoryDao.getCategory(aHash);

        if(categories.size() != 0){
            // TODO Exception
        }

        return categories.get(0);
    }

    public void updateCategoryName(Category aCategory){
        categoryDao.save(aCategory);
    }

    public void addCategory(Category aCategory){
        categoryDao.save(aCategory);
    }

    public void deleteCategory(Category aCategory){

        List<Entry> entries = entryDao.getAllByCategory(aCategory);

        for(Entry entry : entries){
            entryDao.delete(entry);
        }

        categoryDao.delete(aCategory);
    }





}
