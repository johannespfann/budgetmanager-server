package de.pfann.budgetmanager.server.restservices.resources.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.model.User;

import java.io.IOException;
import java.util.List;

public class UserJsonMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToJson(User aUser){
        try {
            return objectMapper.writeValueAsString(aUser);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    public static String convertToJson(List<User> aAppUser){
        try {


            return objectMapper.writeValueAsString(aAppUser);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "[]";
    }

    public static User convertToEntry(String aValue){
        try {
            return objectMapper.readValue(aValue,User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
