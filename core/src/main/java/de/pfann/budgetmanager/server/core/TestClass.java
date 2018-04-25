package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.common.util.DateUtil;
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

        gehaltFeb = peristEntry(johannesUser,gehaltFebTags, "450","ohne steuern");

        List<Tag> urlaubWinterTags = new LinkedList<>();
        urlaubWinterTags.add(tagLuxus);
        urlaubWinterTags.add(tagGoodIdea);
        urlaubWinter = peristEntry(johannesUser, urlaubWinterTags, "120","urlaub für den Winter");



        putzmittel = peristEntry(johannesUser, new ArrayList<>(), "3.50","fürs putzen");
        staubsauger = peristEntry(johannesUser, new ArrayList<>(), "-99","einmaliger Kauf - zu teuer");


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

        RotationEntry rotationEntryGehalt = RotationEntry.generate();
        List<TagTemplate> gehaltTags = new LinkedList<>();
        gehaltTags.add(new TagTemplate("gehalt"));
        gehaltTags.add(new TagTemplate("datev"));
        rotationEntryGehalt.setTags(gehaltTags);
        rotationEntryGehalt.setStart_at(DateUtil.firstDayOfYear(2018));
        //rotationEntryGehalt.setEnd_at(DateUtil.getDateOfYYMMDD(2018,3,17));
        rotationEntryGehalt.setMemo("monatliches Gehalt von der Datev");
        rotationEntryGehalt.setUser(johannesUser);
        rotationEntryGehalt.setAmount("2472.97");
        rotationEntryGehalt.setRotation_strategy("66122");
        rotationEntryFacade.save(rotationEntryGehalt);

        RotationEntry rotatiofahrtkostenGehalt = RotationEntry.generate();
        List<TagTemplate> fahrtkostenTags = new LinkedList<>();
        fahrtkostenTags.add(new TagTemplate("fahrtkosten"));
        fahrtkostenTags.add(new TagTemplate("fixkosten"));
        fahrtkostenTags.add(new TagTemplate("datev"));
        rotatiofahrtkostenGehalt.setTags(fahrtkostenTags);
        rotatiofahrtkostenGehalt.setStart_at(DateUtil.firstDayOfYear(2018));
        //rotationEntryGehalt.setEnd_at(DateUtil.getDateOfYYMMDD(2018,3,17));
        rotatiofahrtkostenGehalt.setMemo("Firmen-Abo VGN");
        rotatiofahrtkostenGehalt.setUser(johannesUser);
        rotatiofahrtkostenGehalt.setAmount("-27.93");
        rotatiofahrtkostenGehalt.setRotation_strategy("66122");
        rotationEntryFacade.save(rotatiofahrtkostenGehalt);

        RotationEntry rotationNetflix = RotationEntry.generate();
        List<TagTemplate> netflixTags = new LinkedList<>();
        netflixTags.add(new TagTemplate("netflix"));
        fahrtkostenTags.add(new TagTemplate("fixkosten"));
        rotationNetflix.setTags(netflixTags);
        rotationNetflix.setStart_at(DateUtil.getDateOfYYMMDD(2018,2,7));
        //rotationEntryGehalt.setEnd_at(DateUtil.getDateOfYYMMDD(2018,3,17));
        rotationNetflix.setMemo("Netflix");
        rotationNetflix.setUser(johannesUser);
        rotationNetflix.setAmount("-13.99");
        rotationNetflix.setRotation_strategy("66122");
        rotationEntryFacade.save(rotationNetflix);


        RotationEntry rotationNetflixMax = RotationEntry.generate();
        List<TagTemplate> netflixMaxTags = new LinkedList<>();
        netflixMaxTags.add(new TagTemplate("netflix"));
        netflixMaxTags.add(new TagTemplate("fixkosten"));
        rotationNetflixMax.setTags(netflixMaxTags);
        rotationNetflixMax.setStart_at(DateUtil.getDateOfYYMMDD(2018,2,7));
        //rotationEntryGehalt.setEnd_at(DateUtil.getDateOfYYMMDD(2018,3,17));
        rotationNetflixMax.setMemo("Netflix");
        rotationNetflixMax.setUser(johannesUser);
        rotationNetflixMax.setAmount("3.50");
        rotationNetflixMax.setRotation_strategy("66122");
        rotationEntryFacade.save(rotationNetflixMax);



    }

    private Entry peristEntry(AppUser appUser, List<Tag> gehaltFebTags, String aAmount, String aMemo) {
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

}
