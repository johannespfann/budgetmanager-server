package de.pfann.budgetmanager.server;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.model.Tag;
import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.daos.CategoryFacade;
import de.pfann.budgetmanager.server.persistens.daos.EntryFacade;
import de.pfann.budgetmanager.server.persistens.daos.TagFacade;
import de.pfann.budgetmanager.server.util.Util;

import java.util.List;
import java.util.Set;
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


    private Tag tagLuxus;
    private Tag tagFix;
    private Tag tagGoodIdea;

    public TestClass(){
        userFacade = new AppUserFacade();
        categoryFacade = new CategoryFacade();
        entryFacade = new EntryFacade();
        tagFacade = new TagFacade();
    }

    public void persistEnviroment(){
        johannesUser = persistUser("johannes-1234","johannes@pfann.de","key");
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        tagLuxus = new Tag("luxus");
        tagFix = new Tag("fixkosten");
        tagGoodIdea = new Tag("guteidee");



        tagFix.setAppUser(johannesUser);
        tagLuxus.setAppUser(johannesUser);
        tagGoodIdea.setAppUser(johannesUser);

        System.out.println("before: " + tagFix.getId());

        tagFacade.persistTag(tagFix);
        tagFacade.persistTag(tagLuxus);
        tagFacade.persistTag(tagGoodIdea);

        System.out.println("after: " + tagFix.getId());

        defaultCategory = categoryFacade.getDefaultCategory(johannesUser);
        gehaltFeb = peristEntry(johannesUser,defaultCategory,450,"ohne steuern");

        // Entry added Tag
        gehaltFeb.addTag(tagFix);
        gehaltFeb.addTag(tagLuxus);
        gehaltFeb.addTag(tagGoodIdea);
        // Entry wird gespeichert
        entryFacade.persistEntry(gehaltFeb);


        urlaubWinter = peristEntry(johannesUser,defaultCategory,120,"urlaub für den Winter");
        //urlaubWinter.addTag(tagLuxus);


        haushaltCategory = persistCategory(johannesUser,"Haushalt");
        putzmittel = peristEntry(johannesUser,haushaltCategory,3.50,"fürs putzen");
        staubsauger = peristEntry(johannesUser,haushaltCategory,300,"einmaliger Kauf - zu teuer");

        putzmittel.addTag(tagFix);
        entryFacade.persistEntry(putzmittel);

        // Alle Entries von einem Tag
        Set<Entry> entries = entryFacade.getEntries(tagFix);

        for (Entry entry: entries){
            System.out.println(entry.getHash() + " : " + entry.getMemo());
        }

        // Alle Tags von einem Entry
        Set<Tag> tags = tagFacade.getTags(gehaltFeb);
        System.out.println("Alle Tags ... ");
        for(Tag tag : tags){
            System.out.println(tag.getName());
        }


    }

    private Entry peristEntry(AppUser appUser, Category aCategory, double aAmount, String aMemo) {
        Entry entry = new Entry();
        entry.setAppUser(appUser);
        entry.setCategory(aCategory);
        entry.setAmount(aAmount);
        entry.setMemo(aMemo);
        entry.setHash(Util.getUniueHash(100000,99999999));
        entryFacade.persistEntry(entry);
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
