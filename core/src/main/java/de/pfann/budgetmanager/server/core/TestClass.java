package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.common.util.Util;
import de.pfann.budgetmanager.server.persistens.daos.*;
import de.pfann.budgetmanager.server.persistens.model.*;


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestClass {


    /**
     * Helper
     */

    private AppUserFacade userFacade;
    private EntryFacade entryFacade;
    private TagFacade tagFacade;
    private RotationEntryFacade rotationEntryFacade;
    private TagTemplateFacade tagTemplateFacade;


    /**
     * Persisted Objects for johannesUser
     */

    private AppUser johannesUser;
    private Entry gehaltFeb;
    private Entry urlaubWinter;


    private Entry putzmittel;
    private Entry staubsauger;


    private Tag tagLuxus;
    private Tag tagFix;
    private Tag tagGoodIdea;

    public TestClass(){
        userFacade = new AppUserFacade();
        entryFacade = new EntryFacade();
        tagFacade = new TagFacade();
        rotationEntryFacade = new RotationEntryFacade();
        tagTemplateFacade = new TagTemplateFacade();
    }

    public void persistEnviroment(){

        johannesUser = persistUser("johannes-1234","johannes@pfann.de","key");
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        tagLuxus = new Tag("luxus");
        tagFix = new Tag("fixkosten");
        tagGoodIdea = new Tag("guteidee");


        AppUser testUser = userFacade.getUserByNameOrEmail("johannes-1234");

        List<Tag> gehaltFebTags = new LinkedList<>();
        gehaltFebTags.add(tagFix);
        gehaltFebTags.add(tagGoodIdea);

        gehaltFeb = peristEntry(johannesUser,gehaltFebTags, 450,"ohne steuern");

        List<Tag> urlaubWinterTags = new LinkedList<>();
        urlaubWinterTags.add(tagLuxus);
        urlaubWinterTags.add(tagGoodIdea);
        urlaubWinter = peristEntry(johannesUser, urlaubWinterTags, 120,"urlaub für den Winter");



        putzmittel = peristEntry(johannesUser, new ArrayList<>(), 3.50,"fürs putzen");
        staubsauger = peristEntry(johannesUser, new ArrayList<>(), 300,"einmaliger Kauf - zu teuer");


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

        RotationEntry rotationEntry = persistRotationEntry(johannesUser);




    }

    private Entry peristEntry(AppUser appUser, List<Tag> gehaltFebTags, double aAmount, String aMemo) {
        Entry entry = new Entry();
        entry.setAppUser(appUser);
        entry.setAmount(aAmount);
        entry.setMemo(aMemo);
        entry.setHash(Util.getUniueHash(100000,99999999));
        entry.setTags(gehaltFebTags);
        entryFacade.persistEntry(entry);
        return entry;
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

    private RotationEntry persistRotationEntry(AppUser aUser){
        RotationEntry entry = new RotationEntry();
        entry.setUser(aUser);
        entry.setStart_at(new Date());
        entry.setLast_executed(null);
        entry.setAmount(2300);
        entry.setMemo("monatliches Gehalt");
        entry.setEnd_at(null);

        TagTemplate tagTemplate01 = new TagTemplate("datev");
        TagTemplate tagTemplate02 = new TagTemplate("einnahmen");


        List<TagTemplate> tags = new ArrayList<>();

        tags.add(tagTemplate01);
        tags.add(tagTemplate02);


        entry.setRotation_strategy("66122");
        entry.setHash(Util.getUniueHash(100,123123123));


        rotationEntryFacade.save(entry);

        tagTemplate01.setRotationEntry(entry);
        tagTemplateFacade.save(tagTemplate01);

        tagTemplate02.setRotationEntry(entry);
        tagTemplateFacade.save(tagTemplate02);


        entry.setTags(tags);
        rotationEntryFacade.save(entry);


        return entry;
}


}
