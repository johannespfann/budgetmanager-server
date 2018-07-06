package de.pfann.budgetmanager.server.persistenscouchdb.util;

import java.time.LocalDateTime;

public class CDBEntryId {

    public static final String SEPERATOR = ":";
    public static final String TYP_PREFIX = "entry";

    private String prefix;

    private String username;

    private String konto;

    private String hash;

    private int year;

    private int month;

    private CDBEntryId(){
        // default
    }

    private CDBEntryId(String aUsername, String aKonto, String aHash, int aYear, int aMonth){
        prefix = TYP_PREFIX;
        username = aUsername;
        konto = aKonto;
        year = aYear;
        month = aMonth;
        hash = aHash;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TYP_PREFIX).append(SEPERATOR)
        .append(username).append(SEPERATOR)
        .append(konto).append(SEPERATOR)
        .append(year).append(SEPERATOR)
        .append(month).append(SEPERATOR)
        .append(hash);
        return stringBuilder.toString();
    }


    public static CDBEntryIdBuilder createBuilder(){
        return new CDBEntryIdBuilder();
    }


    /**
     * getter
     */
    public String getPrefix() {
        return prefix;
    }

    public String getUsername() {
        return username;
    }

    public String getKonto() {
        return konto;
    }

    public String getHash() {
        return hash;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    /**
     * Builder
     */
    public static class CDBEntryIdBuilder {

        private String username;

        private String konto;

        private String hash;

        private int year;

        private int month;

        private CDBEntryIdBuilder(){
            // default
        }

        public CDBEntryIdBuilder withUsername(String aUsername){
            username = aUsername;
            return this;
        }

        public CDBEntryIdBuilder withKonto(String aKonto){
            konto = aKonto;
            return this;
        }

        public CDBEntryIdBuilder withHash(String aHash){
            hash = aHash;
            return this;
        }

        public CDBEntryIdBuilder withCreatedAt(LocalDateTime aLocalDateTime){
            year = aLocalDateTime.getYear();
            month = aLocalDateTime.getMonth().getValue();
            return this;
        }

        public CDBEntryId build(){

            assertUserNameIsValid(username);
            assertKontoIsValid(konto);
            assertHashIsValid(hash);
            assertYearIsValid(year);
            assertMonthIsValid(month);

            CDBEntryId entry = new CDBEntryId(username,konto,hash,year,month);
            return entry;
        }

        public CDBEntryId build(String aValue){
            String[] values = aValue.split(SEPERATOR);
            assertPrefixIsValid(values[0]);
            username = values[1];
            konto = values[2];
            year = Integer.valueOf(values[3]);
            month = Integer.valueOf(values[4]);
            hash = values[5];
            return build();
        }

        private void assertPrefixIsValid(String aValue) {
            if(aValue == null || !aValue.equals(TYP_PREFIX)){
                throw new IllegalArgumentException();
            }
        }

        private void assertMonthIsValid(int aMonth) {
            if(aMonth <= 0){
                throw new IllegalArgumentException();
            }
        }

        private void assertYearIsValid(int aYear) {
            if(aYear <= 0){
                throw new IllegalArgumentException();
            }
        }

        private void assertHashIsValid(String aHash) {
            if(aHash == null || aHash.isEmpty()){
                throw new IllegalArgumentException();
            }
        }

        private void assertKontoIsValid(String aKonto) {
            if(aKonto == null || aKonto.isEmpty()){
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
