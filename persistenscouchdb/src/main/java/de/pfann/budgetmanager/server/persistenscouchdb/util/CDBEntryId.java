package de.pfann.budgetmanager.server.persistenscouchdb.util;

import java.time.LocalDateTime;

public class CDBEntryId {

    public static final String SEPERATOR = ":";
    public static final String TYP_PREFIX = "entry";

    private String prefix;

    private String username;

    private String hash;

    private CDBEntryId(){
        // default
    }

    private CDBEntryId(String aUsername, String aHash){
        prefix = TYP_PREFIX;
        username = aUsername;
        hash = aHash;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(TYP_PREFIX).append(SEPERATOR)
                .append(username).append(SEPERATOR)
                .append(hash);
        return stringBuilder.toString();
    }


    public static CDBEntryIdBuilder createBuilder(){
        return new CDBEntryIdBuilder();
    }


    // TODO delete getter
    /**
     * getter
     */
    public String getPrefix() {
        return prefix;
    }

    public String getUsername() {
        return username;
    }


    public String getHash() {
        return hash;
    }



    /**
     * Builder
     */
    public static class CDBEntryIdBuilder {

        private String username;

        private String hash;

        private CDBEntryIdBuilder(){
            // default
        }

        public CDBEntryIdBuilder withUsername(String aUsername){
            username = aUsername;
            return this;
        }


        public CDBEntryIdBuilder withHash(String aHash){
            hash = aHash;
            return this;
        }

        public CDBEntryId build(){
            assertUserNameIsValid(username);
            assertHashIsValid(hash);

            CDBEntryId entry = new CDBEntryId(username,hash);
            return entry;
        }

        public CDBEntryId build(String aValue){
            String[] values = aValue.split(SEPERATOR);
            assertPrefixIsValid(values[0]);
            username = values[1];
            hash = values[2];
            return build();
        }

        private void assertPrefixIsValid(String aValue) {
            if(aValue == null || !aValue.equals(TYP_PREFIX)){
                throw new IllegalArgumentException();
            }
        }

        private void assertHashIsValid(String aHash) {
            if(aHash == null || aHash.isEmpty()){
                throw new IllegalArgumentException();
            }
        }

        private void assertUserNameIsValid(String aUsername) {
            if(aUsername == null || aUsername.isEmpty()){
                throw new IllegalArgumentException();
            }
        }
    }
}
