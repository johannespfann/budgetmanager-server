package de.pfann.budgetmanager.server.persistens;

public class CategoryDao extends AbstractDao {

    public static CategoryDao create(){
        return new CategoryDao(DbWriter.create(),DbReader.create());
    }

    protected CategoryDao(DbWriter _dbWriter, DbReader _dbReader) {
        super(_dbWriter, _dbReader);
    }

    @Override
    protected Class<?> getEntityClass() {
        return null;
    }
}
