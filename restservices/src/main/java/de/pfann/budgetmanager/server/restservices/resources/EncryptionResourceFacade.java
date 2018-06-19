package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.daos.AppUserSQLFacade;

public class EncryptionResourceFacade {

    private AppUserSQLFacade userFacade;

    public EncryptionResourceFacade(){
        userFacade = new AppUserSQLFacade();
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
            return user.getEncryptTest();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public void setEncryptionText(String aOwner, String aBody){
        try{
            AppUser user = userFacade.getUserByNameOrEmail(aOwner);

            LogUtil.info(this.getClass(),"Set text: " + aBody);
            user.setEncryptTest(aBody);
            user.setEncrypted(true);

            userFacade.updateUser(user);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
