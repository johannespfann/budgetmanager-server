package de.pfann.budgetmanager.server.persistenscouchdb.file.writer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.facade.EntryFacade;
import de.pfann.budgetmanager.server.common.facade.StandingOrderFacade;
import de.pfann.budgetmanager.server.common.facade.TagStatisticFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.StandingOrder;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class JSONFileWriter {

    private AppUserFacade userFacade;
    private EntryFacade entryFacade;
    private StandingOrderFacade standingOrderFacade;
    private TagStatisticFacade tagStatisticFacade;

    public JSONFileWriter(AppUserFacade aUserFacade,
                          EntryFacade aEntryFacade,
                          StandingOrderFacade aStandingOrderFacade,
                          TagStatisticFacade aStatisticFacade){
        userFacade = aUserFacade;
        entryFacade = aEntryFacade;
        standingOrderFacade = aStandingOrderFacade;
        tagStatisticFacade = aStatisticFacade;
    }

    public void writeUserDataToFile(String aUserName, String aOutputDirektory){
        AppUser user = userFacade.getUserByNameOrEmail(aUserName);

        // Get all StandingOrder of specific user
        List<StandingOrder> standingOrders = new LinkedList<>();
        standingOrders = standingOrderFacade.getRotationEntries(user);


        // Convert StandingOrders to JSON-String
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(standingOrders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // create new directory for specific user
        String directoryPath = aOutputDirektory  + user.getName();
        System.out.println(directoryPath);
        File directory =  new File(directoryPath);
        boolean fileCreated = directory.mkdirs();

        System.out.println(fileCreated);

        // write all standingorders to file
        FileUtil.createFile(directoryPath + "\\standingorder.json", json);

        // Get all entries of specific user
        List<Entry> entries = new LinkedList<>();
        entries = entryFacade.getEntries(user);

        System.out.println("Entries " + entries.size());

        // package all entries
        PackageManager packageManager = new PackageManager();

        for(Entry entry : entries){
            packageManager.add(entry);
        }

        List<EntryPackage> entryPackages = packageManager.getEntryPackages();

        // create files for each package of entries
        for(EntryPackage singlePackage : entryPackages){
            System.out.println("Create new File with " + singlePackage.getEntries().size() + " entries");
            String fileName = "entry- " + singlePackage.getLocalDateTime().getYear() + "-" + singlePackage.getLocalDateTime().getMonth().getValue() +".json";
            String entryJson = "";

            try {
                entryJson = objectMapper.writeValueAsString(singlePackage.getEntries());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            FileUtil.createFile(directoryPath + "\\" + fileName,entryJson);
        }

    }

}
