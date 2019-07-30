package de.pfann.budgetmanager.server.persistenscouchdb.file;

public class JSONCouchDBWriter {

    /*
    private CDBUserFacade userFacade;
    private EntryFacade entryFacade;
    private StandingOrderFacade standingOrderFacade;
    private CDBStatisticFacade tagStatisticFacade;


    public JSONCouchDBWriter(CDBUserFacade aUserFacade,
                          EntryFacade aEntryFacade,
                          StandingOrderFacade aStandingOrderFacade,
                          CDBStatisticFacade aStatisticFacade){
        userFacade = aUserFacade;
        entryFacade = aEntryFacade;
        standingOrderFacade = aStandingOrderFacade;
        tagStatisticFacade = aStatisticFacade;
    }

    public void writeUserdataToCouchDb(File aDirectory){
        ObjectMapper objectMapper = new ObjectMapper();

        File userFile = new File(aDirectory + "\\user.json");

        String userContent = FileUtil.convertToString(userFile);

        System.out.println(userContent);
        CDBUser cdbUser = null;
        try {
            //user = objectMapper.readValue(content, new TypeReference<List<Entry>>(){} );
            cdbUser = objectMapper.readValue(userContent, CDBUser.class );
        } catch (IOException e) {
            e.printStackTrace();
        }

        AppUser appUser = new AppUser();
        appUser.setName(cdbUser.getUsername());
        appUser.setPassword(cdbUser.getPassword());
        appUser.setEmail(cdbUser.getEmails().get(0));

        userFacade.createNewUser(appUser);
        userFacade.updateUser(cdbUser);

        File standingOrderFile = new File(aDirectory + "\\standingorder.json");
        String standingOrderContent = FileUtil.convertToString(standingOrderFile);
        System.out.println(standingOrderFile);

        List<StandingOrder> standingOrders = null;
        try {
            standingOrders = objectMapper.readValue(standingOrderContent, new TypeReference<List<StandingOrder>>(){} );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Persist all standing orders

        for(StandingOrder standingOrder : standingOrders){
            System.out.println("Persist StandingOrder with hash" + standingOrder.getHash());
            System.out.println(standingOrder.toString());
            standingOrder.setUser(appUser);
            standingOrderFacade.save(standingOrder);
            standingOrderFacade.update(standingOrder);
        }

        List<String> entryNames = getAllEntryFiles(aDirectory);

        List<Entry> allEntries = new LinkedList<>();

        for(String value : entryNames){
            System.out.println("Collect: " + value);

            File entryFile = new File(aDirectory + "\\" + value);
            String entriesContent =  FileUtil.convertToString(entryFile);
            List<Entry> entriesOfFile = new LinkedList<>();

            try {
                entriesOfFile = objectMapper.readValue(entriesContent, new TypeReference<List<Entry>>(){});
            } catch (IOException e) {
                e.printStackTrace();
            }
            allEntries.addAll(entriesOfFile);
        }

        for(Entry entry : allEntries){
            System.out.println("Persist Entry" + entry.getHash());
            entry.setAppUser(appUser);
            entryFacade.persistEntry(entry);
        }

    }

    private List<String> getAllEntryFiles(File aDirectory){

        List<String> fileNames = new LinkedList<>();

        for(File child : aDirectory.listFiles()){
            if(child.getName().contains("entry")) {
                fileNames.add(child.getName());
            }
        }

        return fileNames;
    }

    */

}
