package de.pfann.budgetmanager.server.persistenscouchdb.file.writer;

import de.pfann.budgetmanager.server.common.model.Entry;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class EntryPackage {

    private LocalDateTime localDateTime;
    private List<Entry> entries;

    public EntryPackage(LocalDateTime aLocalDateTime){
        localDateTime = aLocalDateTime;
        entries = new LinkedList<>();
    }

    public void add(Entry aEntry){
        entries.add(aEntry);
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public List<Entry> getEntries() {
        return entries;
    }
}
