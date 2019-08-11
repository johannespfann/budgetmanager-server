package de.pfann.budgetmanager.server.persistenscouchdb.file;

import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.common.util.DateUtil;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class PackageBundler {

    private List<EntryPackage> entryPackages;

    public PackageBundler(){
        entryPackages = new LinkedList<>();
    }

    public void add(List<Entry> entries) {
        for(Entry entry : entries){
            add(entry);
        }
    }

    public void add(Entry aEntry){
        LocalDateTime localDateTime = DateUtil.asLocalDateTime(aEntry.getCreatedAt());

        EntryPackage entryPackage;

        if(!packageExists(localDateTime)){
            LocalDateTime newLocalDateTime = LocalDateTime.of(
                    localDateTime.getYear(),
                    localDateTime.getMonth().getValue(),
                    localDateTime.getDayOfMonth(),0,0,0);

            entryPackage = new EntryPackage(newLocalDateTime);
            entryPackages.add(entryPackage);
        }
        else {
            entryPackage = getEntryPackage(localDateTime);
        }

        entryPackage.add(aEntry);
    }

    public List<EntryPackage> getEntryPackages(){
        return entryPackages;
    }

    private EntryPackage getEntryPackage(LocalDateTime aLocalDateTime) {

        for(EntryPackage entryPackage : entryPackages){
            if(entryPackage.getLocalDateTime().getYear() == aLocalDateTime.getYear() && entryPackage.getLocalDateTime().getMonth().getValue() == aLocalDateTime.getMonth().getValue()){
                return entryPackage;
            }
        }

        return null;
    }

    private boolean packageExists(LocalDateTime aLocalDateTime) {

        for(EntryPackage entryPackage : entryPackages){
            if(entryPackage.getLocalDateTime().getYear() == aLocalDateTime.getYear() && entryPackage.getLocalDateTime().getMonth().getValue() == aLocalDateTime.getMonth().getValue()){
                return true;
            }
        }

        return false;
    }


}
