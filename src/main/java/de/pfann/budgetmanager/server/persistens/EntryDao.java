package de.pfann.budgetmanager.server.persistens;

import de.pfann.budgetmanager.server.model.Entry;

import java.util.List;

public class EntryDao extends AbstractDao {

    public static EntryDao create() {
        return new EntryDao(DbWriter.create(), DbReader.create());
    }

    protected EntryDao(DbWriter _dbWriter, DbReader _dbReader) {
        super(_dbWriter, _dbReader);
    }

    @Override
    protected Class<?> getEntityClass() {
        return Entry.class;
    }

    public List<Entry> getAll() {
        return (List<Entry>) doGetAll();
    }


}
