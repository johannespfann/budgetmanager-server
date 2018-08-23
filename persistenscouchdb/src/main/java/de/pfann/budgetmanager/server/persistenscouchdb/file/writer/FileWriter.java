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
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBUser;

import java.util.LinkedList;
import java.util.List;

public class FileWriter {

    private AppUserFacade userFacade;
    private EntryFacade entryFacade;
    private StandingOrderFacade standingOrderFacade;
    private TagStatisticFacade tagStatisticFacade;

    public FileWriter(AppUserFacade aUserFacade,
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

        // Get all entries of specific user
        List<Entry> entries = new LinkedList<>();
        entries = entryFacade.getEntries(user);


        // Convert StandingOrders to JSON-String
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(standingOrders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(json);


    }

}
