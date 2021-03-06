package de.pfann.budgetmanager.server.persistenscouchdb.util;

public class CDBStandingOrderId {

    private static final String SEPERATOR = ":";
    private static final String TYP_PREFIX = "standingorder";

    private String prefix;

    private String username;

    private String hash;

    private CDBStandingOrderId(){
        // default
    }

    private CDBStandingOrderId(String aUsername, String aHash){
        prefix = TYP_PREFIX;
        username = aUsername;
        hash = aHash;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TYP_PREFIX).append(SEPERATOR)
                .append(username).append(SEPERATOR)
                .append(hash);
        return stringBuilder.toString();
    }

    public static CDBStandingOrderBuilder createBuilder(){
        return new CDBStandingOrderBuilder();
    }

    /**
     * Getter
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
    public static class CDBStandingOrderBuilder {

        private String username;

        private String hash;


        private CDBStandingOrderBuilder(){
            // default
        }

        public CDBStandingOrderBuilder withUsername(String aUsername){
            username = aUsername;
            return this;
        }

        public CDBStandingOrderBuilder withHash(String aHash){
            hash = aHash;
            return this;
        }


        public CDBStandingOrderId build(){
            assertUserNameIsValid(username);
            assertHashIsValid(hash);

            CDBStandingOrderId entry = new CDBStandingOrderId(username,hash);
            return entry;
        }

        public CDBStandingOrderId build(String aValue){
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
