package de.pfann.budgetmanager.server.persistens;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Tag;

import java.util.LinkedList;
import java.util.List;

public class TagDao extends AbstractDao{

    protected TagDao(DbWriter _dbWriter, DbReader _dbReader) {
        super(_dbWriter, _dbReader);
    }

    @Override
    protected Class<?> getEntityClass() {
        return Tag.class;
    }

    public List<Tag> getAllTagsByUser(AppUser aUser){
        return new LinkedList<>();
    }

    public static TagDao create() {
        return new TagDao(DbWriter.create(),DbReader.create());
    }
}
