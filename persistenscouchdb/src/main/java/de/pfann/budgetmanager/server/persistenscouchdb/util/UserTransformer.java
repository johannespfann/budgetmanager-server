package de.pfann.budgetmanager.server.persistenscouchdb.util;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBUser;

import java.util.List;

public class UserTransformer {

    public static AppUser createAppUser(CDBUser aUser) {
        AppUser appUser = new AppUser();
        appUser.setName(aUser.getUsername());
        appUser.setEmail(aUser.getEmails().get(0));
        appUser.setEncrypted(aUser.isUSerEncrypted());
        appUser.setEncryptionText(aUser.getEncryptiontext());
        appUser.setPassword(aUser.getPassword());

        if(aUser.isActivated()) {
            appUser.activate();
        }

        return appUser;
    }

    public static CDBUser updateCDBUser(AppUser aAppUser, CDBUser aUser){
        aUser.setUsername(aAppUser.getName());
        aUser.setPassword(aAppUser.getPassword());
        aUser.setEncryptionText(aAppUser.getEncryptionText());

        List<String> emails = aUser.getEmails();

        if(!emails.contains(aAppUser.getEmail())){
            emails.clear();
            emails.add(aAppUser.getEmail());
        }

        if(aAppUser.isActivated()){
            aUser.activate();
        }

        return aUser;
    }
}
