package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.model.Entry;

import java.util.List;

public class CategoryFacade {

    private CategoryDao categoryDao;

    private EntryDao entryDao;

    private AppUserDao userDao;

    public CategoryFacade(){
        categoryDao = CategoryDao.create();
        entryDao = EntryDao.create();
        userDao = AppUserDao.create();
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

    public void deleteCategory(Category aCategoryToDelete, Category aCategoryToReplace) {

        // 1 Get all Entries to replace categories

        List<Entry> entries = entryDao.getAllByCategory(aCategoryToDelete);

        // 2 Replace all entries with new Category
        for(Entry entry : entries){
            entry.setCategory(aCategoryToReplace);
            entryDao.save(entry);
        }

        // 3 Delete all entries

        categoryDao.delete(aCategoryToDelete);
    }


    public Category getDefaultCategory(AppUser aUser) {
        Category defaultCategory = aUser.getDefaultCategory();
        return defaultCategory;
    }
}
