package de.pfann.budgetmanager.server;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.daos.CategoryFacade;
import de.pfann.budgetmanager.server.persistens.daos.EntryFacade;
import de.pfann.budgetmanager.server.persistens.daos.TagFacade;
import de.pfann.budgetmanager.server.util.Util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestClass {


    /**
     * Helper
     */

    private AppUserFacade userFacade;
    private CategoryFacade categoryFacade;
    private EntryFacade entryFacade;
    private TagFacade tagFacade;


    /**
     * Persisted Objects for johannesUser
     */

    private AppUser johannesUser;
    private Category defaultCategory;
    private Entry gehaltFeb;
    private Entry urlaubWinter;


    private Category haushaltCategory;
    private Entry putzmittel;
    private Entry staubsauger;

    public TestClass(){
        userFacade = new AppUserFacade();
        categoryFacade = new CategoryFacade();
        entryFacade = new EntryFacade();
        tagFacade = new TagFacade();
    }

    public void persistEnviroment(){
        johannesUser = persistUser("johannes-1234","johannes@pfann.de","key");
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        defaultCategory = categoryFacade.getDefaultCategory(johannesUser);
        gehaltFeb = peristEntry(johannesUser,defaultCategory,450,"ohne steuern");
        urlaubWinter = peristEntry(johannesUser,defaultCategory,120,"urlaub für den Winter");

        haushaltCategory = persistCategory(johannesUser,"Haushalt");
        putzmittel = peristEntry(johannesUser,haushaltCategory,3.50,"fürs putzen");
        staubsauger = peristEntry(johannesUser,haushaltCategory,300,"einmaliger Kauf - zu teuer");

    }

    private Entry peristEntry(AppUser appUser, Category aCategory, double aAmount, String aMemo) {
        Entry entry = new Entry();
        entry.setAppUser(appUser);
        entry.setCategory(aCategory);
        entry.setAmount(aAmount);
        entry.setMemo(aMemo);
        entry.setHash(Util.getUniueHash(100000,99999999));
        entryFacade.addEntry(entry);
        return entry;
    }

    private Category persistCategory(AppUser aUser, String aName){
        Category category = new Category();
        category.setHash(Util.getUniueHash(100000,99999999));
        category.setAppUser(aUser);
        category.setName(aName);
        categoryFacade.addCategory(category);
        return category;
    }

    private AppUser persistUser(String aUserName, String aEmail, String aPW) {
        AppUser user = new AppUser();
        user.setName(aUserName);
        user.setEmail(aEmail);
        user.setPassword(aPW);
        userFacade.createNewUser(user);
        userFacade.activateUser(user);
        return user;
    }


}
