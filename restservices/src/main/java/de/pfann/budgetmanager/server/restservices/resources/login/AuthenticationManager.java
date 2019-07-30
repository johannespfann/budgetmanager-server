package de.pfann.budgetmanager.server.restservices.resources.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.EncryptUtil;

import java.io.IOException;
import java.time.LocalDateTime;

public class AuthenticationManager {

    private String key = "key";

    public AuthenticationManager( ){

    }

    public boolean isValidToken(String aIdentifier, String aToken) throws IOException {
        // entschlüssel token
        String dencryptedToken = EncryptUtil.decrypt(aToken, key);
        // map json zu object
        ObjectMapper objectMapper = new ObjectMapper();
        AuthToken authToken = objectMapper.readValue(dencryptedToken,AuthToken.class);
        // validate token
        if(!aIdentifier.equals(authToken.getUsername())){
            System.out.println("username was not valid " + aIdentifier);
            return false;
        }
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime expireDate = DateUtil.asLocalDateTime(authToken.getExpiredate());

        if(today.isAfter(expireDate)){
            System.out.println("heute war nach expire");
            return false;
        }

        return true;
    }

    public String generateToken(String aAppUser){
        LocalDateTime today = LocalDateTime.now();
        AuthToken authToken = new AuthToken();
        authToken.setUsername(aAppUser);
        authToken.setExpiredate(DateUtil.asDate(today.plusDays(2)));

        ObjectMapper objectMapper = new ObjectMapper();
        String generatedToken = null;
        try {
            generatedToken = objectMapper.writeValueAsString(authToken);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String encryptToken = EncryptUtil.encrypt(generatedToken, key);
        return encryptToken;
    }




}
