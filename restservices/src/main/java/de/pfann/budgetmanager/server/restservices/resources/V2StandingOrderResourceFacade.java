package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.facade.AccountFacade;
import de.pfann.budgetmanager.server.common.facade.StandingOrder2Facade;
import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.StandingOrder;


import java.util.List;

public class V2StandingOrderResourceFacade {

    private StandingOrder2Facade standingOrderFacade;
    private AccountFacade accountFacade;

    public V2StandingOrderResourceFacade(AccountFacade aAccountFacade, StandingOrder2Facade aStandingOrderFacade) {
        standingOrderFacade = aStandingOrderFacade;
        accountFacade = aAccountFacade;
    }

    public List<StandingOrder> getRotationEntries(String aOwner, String aAccountHash) {
        try{
            Account account = accountFacade.getAccount(aOwner,aAccountHash);
            return standingOrderFacade.getStandingOrders(account);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public void addRotationEntry(String aOwner, String aAccountHash, StandingOrder aEntry) {
        try{
            Account account = accountFacade.getAccount(aOwner,aAccountHash);
            standingOrderFacade.persist(account,aEntry);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteRotationEntry(String aOwner, String aAccountHash, String aHash) {
        try{
            Account account = accountFacade.getAccount(aOwner,aAccountHash);
            standingOrderFacade.delete(account,aHash);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public void updateRotationEntry(String aOwner, String aAccountHash, StandingOrder aStandingOrder) {
        try{
            Account account = accountFacade.getAccount(aOwner,aAccountHash);
            standingOrderFacade.update(account,aStandingOrder);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public void addRotationEntries(String aOwner, String aAccountHash, List<StandingOrder> aEntries) {
        try {
            Account account = accountFacade.getAccount(aOwner,aAccountHash);

            for(StandingOrder entry : aEntries) {
                standingOrderFacade.persist(account,entry);
            }

        } catch (Exception e){
        e.printStackTrace();
        throw e;
    }
    }
}
