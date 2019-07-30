package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;


public class EncryptionResourceFacade {

    private AppUserFacade userFacade;

    public EncryptionResourceFacade(AppUserFacade aAppUserFacade){
        userFacade = aAppUserFacade;
    }

    public boolean isEncrypted(String aOwner){
        try{
            AppUser user = userFacade.getUserByNameOrEmail(aOwner);
            return user.isEncrypted();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public String getEncryptionText(String aOwner){
        try{
            AppUser user = userFacade.getUserByNameOrEmail(aOwner);
            return user.getEncryptionText();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public void setEncryptionText(String aOwner, String aBody){
        try{
            AppUser user = userFacade.getUserByNameOrEmail(aOwner);
            user.setEncryptionText(aBody);
            user.setEncrypted(true);
            userFacade.updateUser(user);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
