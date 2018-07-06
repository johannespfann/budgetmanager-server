package de.pfann.budgetmanager.server.persistenscouchdb.util;

public class CDBKontoDatabaseId {

    public static final String APPLICATION_PREFIX = "bm";
    public static final String SEPERATOR = "-";


    private String username;
    private String kontoHash;


    private CDBKontoDatabaseId(String aUsername, String aKontoHash){
        username = aUsername;
        kontoHash = aKontoHash;
    }

    public String toString(){
        return APPLICATION_PREFIX + SEPERATOR + username + SEPERATOR + kontoHash;
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

    public String getKontoHash() {
        return kontoHash;
    }

    /**
     * builder
     */

    public static class CDBKontoDatabaseIdBuilder {

        private String username;
        private String kontoHash;

        public CDBKontoDatabaseIdBuilder(){
            // default
        }

        public CDBKontoDatabaseIdBuilder withUsername(String aUsername){
            username = aUsername;
            return this;
        }

        public CDBKontoDatabaseIdBuilder withKontoHash(String aKontoHash){
            kontoHash = aKontoHash;
            return this;
        }

        public CDBKontoDatabaseId build(){
            assertUsernameIsValid(username);
            assertDatabaseNameIsValid(kontoHash);
            return new CDBKontoDatabaseId(username, kontoHash);
        }

        public CDBKontoDatabaseId build(String value){
            String[] values = value.split(SEPERATOR);
            assertPrefixIsValid(values[0]);
            username = values[1];
            kontoHash = values[2];
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
