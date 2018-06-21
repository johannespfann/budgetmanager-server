package de.pfann.budgetmanager.server.persistenscouchdb.util;

public class CDBKontoDatabaseId {

    public static final String APPLICATION_PREFIX = "bm";
    public static final String SEPERATOR = ":";


    private String username;
    private String databasename;


    private CDBKontoDatabaseId(String aUsername, String aDatabaseName){
        username = aUsername;
        databasename = aDatabaseName;
    }

    public String toString(){
        return APPLICATION_PREFIX + SEPERATOR + username + SEPERATOR + databasename;
    }

    public static CDBKontoDatabaseIdBuilder builder(){
        return new CDBKontoDatabaseIdBuilder();
    }

    /**
     * getter
     */

    public String getUsername() {
        return username;
    }

    public String getDatabasename() {
        return databasename;
    }

    /**
     * builder
     */

    public static class CDBKontoDatabaseIdBuilder {

        private String username;
        private String databasename;

        public CDBKontoDatabaseIdBuilder(){
            // default
        }

        public CDBKontoDatabaseIdBuilder withUsername(String aUsername){
            username = aUsername;
            return this;
        }

        public CDBKontoDatabaseIdBuilder withDatabaseName(String aDatabaseName){
            databasename = aDatabaseName;
            return this;
        }

        public CDBKontoDatabaseId build(){
            assertUsernameIsValid(username);
            assertDatabaseNameIsValid(databasename);
            return new CDBKontoDatabaseId(username,databasename);
        }

        public CDBKontoDatabaseId build(String value){
            String[] values = value.split(SEPERATOR);
            assertPrefixIsValid(values[0]);
            username = values[1];
            databasename = values[2];
            return build();
        }

        private void assertPrefixIsValid(String aPrefix) {
            if(aPrefix == null || aPrefix.isEmpty() || !aPrefix.equals(APPLICATION_PREFIX)){
                throw new IllegalArgumentException();
            }
        }

        private void assertDatabaseNameIsValid(String aDatabasename) {
            if(aDatabasename == null || aDatabasename.isEmpty()){
                throw new IllegalArgumentException();
            }
        }

        private void assertUsernameIsValid(String aUsername) {
            if(username == null || username.isEmpty()){
                throw new IllegalArgumentException();
            }
        }

    }
}
