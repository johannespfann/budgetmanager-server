package de.pfann.budgetmanager.server.restservices.resources.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.common.model.AppUser;

import java.io.IOException;
import java.util.List;

public class UserJsonMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToJson(AppUser aAppUser){
        try {
            return objectMapper.writeValueAsString(aAppUser);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    public static String convertToJson(List<AppUser> aAppUser){
        try {
            return objectMapper.writeValueAsString(aAppUser);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "[]";
    }

    public static AppUser convertToEntry(String aValue){
        try {
            return objectMapper.readValue(aValue,AppUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
