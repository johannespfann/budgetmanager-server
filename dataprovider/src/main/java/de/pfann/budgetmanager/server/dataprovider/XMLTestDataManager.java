package de.pfann.budgetmanager.server.dataprovider;

import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.facade.EntryFacade;
import de.pfann.budgetmanager.server.common.facade.StandingOrderFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.StandingOrder;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class XMLTestDataManager {

    private AppUserFacade userFacade;
    private StandingOrderFacade standingOrderFacade;
    private EntryFacade entryFacade;

    private XMLStandingOrderReader standingOrderReader;
    private XMLEntryReader entryReader;
    private XMLUserReader userReader;

    public XMLTestDataManager(StandingOrderFacade aStandingOrderFacade, EntryFacade aEntryFacade, AppUserFacade aUserFacade){
        standingOrderFacade = aStandingOrderFacade;
        userFacade = aUserFacade;
        entryFacade = aEntryFacade;
        standingOrderReader = new XMLStandingOrderReader();
        userReader = new XMLUserReader();
        entryReader = new XMLEntryReader();
    }

    private XMLTestDataManager(){
        standingOrderReader = new XMLStandingOrderReader();
        userReader = new XMLUserReader();
        entryReader = new XMLEntryReader();
    }

    public void persistTestData(String aRootPath) throws ParserConfigurationException, SAXException, IOException {

        // Path
        File file = new File(aRootPath);
        File[] files = file.listFiles();

        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                // leafe
            } else if (files[i].isDirectory()) {
                persistTestDataForOneUser(files[i]);
            }
        }
    }

    private void persistTestDataForOneUser(File aFile) throws IOException, SAXException, ParserConfigurationException {

        System.out.println("Persist testdata for username: " + aFile.getName());

        File[] files = aFile.listFiles();

        AppUser user = new AppUser();
        List<StandingOrder> standingOrders = new LinkedList<>();
        List<Entry> entries = new LinkedList<>();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                if(files[i].getName().equals("standingorder.xml")){
                    List<StandingOrder> standingOrdersOfSingleXML = standingOrderReader.getStandingOrders(files[i]);
                    standingOrders.addAll(standingOrdersOfSingleXML);
                }else if(files[i].getName().equals("user.xml")){
                    user = createUser(files[i]);
                    user.setName(aFile.getName());
                }else{
                    List<Entry> entriesOfSingleXML = entryReader.getEntries(files[i]);
                    entries.addAll(entriesOfSingleXML);
                }
            }
        }

        System.out.println("CreateUser");
        userFacade.createNewUser(user);
        System.out.println("activate");
        userFacade.activateUser(user);

        for(Entry entry : entries){
            entry.setAppUser(user);
            System.out.println("Persist entry: " + entry.toString());
            entryFacade.persistEntry(entry);
        }

        for(StandingOrder order : standingOrders){
            order.setUser(user);
            System.out.println("Persist StandingOrder " + order.toString());
            standingOrderFacade.save(order);
        }
    }

    private AppUser createUser(File aFile) throws IOException, SAXException, ParserConfigurationException {
        return userReader.getUser(aFile,aFile.getName());
    }


}
