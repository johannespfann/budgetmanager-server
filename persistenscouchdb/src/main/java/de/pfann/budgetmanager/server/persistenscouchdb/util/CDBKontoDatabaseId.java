package de.pfann.budgetmanager.server.persistenscouchdb.util;

public class CDBKontoDatabaseId {

    public static final String SEPERATOR = "_";

    private String username;
    private String typ;
    private String kontoHash;


    private CDBKontoDatabaseId(String aUsername, String aKontoHash, String aTyp){
        username = aUsername;
        kontoHash = aKontoHash;
        typ = aTyp;
    }

    public String toString(){
        return username + SEPERATOR + kontoHash + SEPERATOR + typ;
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

    public String getTyp() {
        return typ;
    }
    /**
     * builder
     */

    public static class CDBKontoDatabaseIdBuilder {

        private String username;
        private String kontoHash;
        private String typ;

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

        public CDBKontoDatabaseIdBuilder withObjectTyp(String aTyp) {
            typ = aTyp;
            return this;
        }

        public CDBKontoDatabaseId build(){
            assertUsernameIsValid(username);
            assertDatabaseNameIsValid(kontoHash);
            assertObjectTypIsValid(typ);
            return new CDBKontoDatabaseId(username, kontoHash, typ);
        }

        public CDBKontoDatabaseId build(String value){
            String[] values = value.split(SEPERATOR);
            username = values[0];
            kontoHash = values[1];
            typ = values[2];
            return build();
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

        private void assertApplicationPrefix(String aApplicationPrefix) {
            if(aApplicationPrefix == null || aApplicationPrefix.isEmpty()){
                throw new IllegalArgumentException();
            }
        }

        private void assertObjectTypIsValid(String aTyp) {
            if(aTyp == null || aTyp.isEmpty()){
                throw new IllegalArgumentException();
            }
        }

    }
}
