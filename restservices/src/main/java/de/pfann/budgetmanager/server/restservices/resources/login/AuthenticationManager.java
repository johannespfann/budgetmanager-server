package de.pfann.budgetmanager.server.restservices.resources.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.EncryptUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

public class AuthenticationManager {

    private String key = "key";
    private AppUserFacade appUserFacader;

    public AuthenticationManager(AppUserFacade aAppUserFacade){
        appUserFacader = aAppUserFacade;
    }

    public boolean isValidToken(String aAppUser, String aToken) throws IOException {
        // entschlüssel token
        String dencryptedToken = EncryptUtil.decrypt(aToken, key);
        // map json zu object
        ObjectMapper objectMapper = new ObjectMapper();
        AuthToken authToken = objectMapper.readValue(dencryptedToken,AuthToken.class);
        // validate token
        if(!aAppUser.equals(authToken.getUsername())){
            System.out.println("username was not valid " + aAppUser);
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
