package de.pfann.budgetmanager.server.restservices.resources.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.model.Account;

import java.io.IOException;
import java.util.List;

public class AccountMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToJson(List<Account> aAccounts){
        try {
            return objectMapper.writeValueAsString(aAccounts);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "[]";
    }

    public static Account convertToAccount(String aBody) {
        try {
            return objectMapper.readValue(aBody, Account.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
