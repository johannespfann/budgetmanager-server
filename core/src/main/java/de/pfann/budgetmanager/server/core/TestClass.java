package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.common.util.Util;
import de.pfann.budgetmanager.server.persistens.model.AppUser;
import de.pfann.budgetmanager.server.persistens.model.Category;
import de.pfann.budgetmanager.server.persistens.model.Entry;
import de.pfann.budgetmanager.server.persistens.model.Tag;
import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.daos.CategoryFacade;
import de.pfann.budgetmanager.server.persistens.daos.EntryFacade;
import de.pfann.budgetmanager.server.persistens.daos.TagFacade;
import de.pfann.budgetmanager.server.persistens.model.RotationEntry;
import de.pfann.budgetmanager.server.persistens.daos.RotationEntryFacade;


import java.util.*;
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
    private RotationEntryFacade rotationEntryFacade;


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
        rotationEntryFacade = new RotationEntryFacade();
    }

    public void persistEnviroment(){
        johannesUser = persistUser("johannes-1234","johannes@pfann.de","key");
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        tagLuxus = new Tag("luxus");
        tagFix = new Tag("fixkosten");
        tagGoodIdea = new Tag("guteidee");


        AppUser testUser = userFacade.getUserByNameOrEmail("johannes-1234");


        defaultCategory = categoryFacade.getDefaultCategory(johannesUser);

        List<Tag> gehaltFebTags = new LinkedList<>();
        gehaltFebTags.add(tagFix);
        gehaltFebTags.add(tagGoodIdea);

        gehaltFeb = peristEntry(johannesUser,defaultCategory,gehaltFebTags, 450,"ohne steuern");

        List<Tag> urlaubWinterTags = new LinkedList<>();
        urlaubWinterTags.add(tagLuxus);
        urlaubWinterTags.add(tagGoodIdea);
        urlaubWinter = peristEntry(johannesUser,defaultCategory, urlaubWinterTags, 120,"urlaub für den Winter");



        haushaltCategory = persistCategory(johannesUser,"Haushalt");
        putzmittel = peristEntry(johannesUser,haushaltCategory, new ArrayList<>(), 3.50,"fürs putzen");
        staubsauger = peristEntry(johannesUser,haushaltCategory, new ArrayList<>(), 300,"einmaliger Kauf - zu teuer");


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

        RotationEntry rotationEntry = persistRotationEntry(johannesUser, haushaltCategory);


    }

    private Entry peristEntry(AppUser appUser, Category aCategory, List<Tag> gehaltFebTags, double aAmount, String aMemo) {
        Entry entry = new Entry();
        entry.setAppUser(appUser);
        entry.setCategory(aCategory);
        entry.setAmount(aAmount);
        entry.setMemo(aMemo);
        entry.setHash(Util.getUniueHash(100000,99999999));
        entry.setTags(gehaltFebTags);
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

    private RotationEntry persistRotationEntry(AppUser aUser, Category aCategory){
        RotationEntry entry = new RotationEntry();
        entry.setUser(aUser);
        entry.setStart_at(new Date());
        entry.setLast_executed(null);
        entry.setAmount(2300);
        entry.setCategory(aCategory);
        entry.setMemo("monatliches Gehalt");
        entry.setEnd_at(null);
        entry.setTags("bla:luxus");
        entry.setRotation_strategy("66122");
        entry.setHash(Util.getUniueHash(100,123123123));

        rotationEntryFacade.save(entry);
        return entry;
    }


}
