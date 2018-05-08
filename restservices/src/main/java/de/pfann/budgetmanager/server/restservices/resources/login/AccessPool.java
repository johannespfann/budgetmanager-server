package de.pfann.budgetmanager.server.restservices.resources.login;

import de.pfann.budgetmanager.server.persistens.model.AppUser;

import java.util.LinkedList;
import java.util.List;

public class AccessPool {

    private static AccessPool instance = null;

    List<AccessTicket> accessTickets;

    private AccessPool(){
        accessTickets = new LinkedList<>();
    }

    public static AccessPool getInstance() {
        if(instance == null){
            return new AccessPool();
        }
        return instance;
    }

    public void unregister(AppUser appUser, String aAccessToken){
        if(isValid(appUser, aAccessToken)){

            AccessTicket ticket = null;

            try {
                ticket = getAccessTicket(appUser);
            } catch (AccessTicketNotFoundException e) {
                e.printStackTrace();
            }

            accessTickets.remove(ticket);
        }
    }

    public void register(AppUser appUser, String aAccessToken) {
        accessTickets.add(new AccessTicket(appUser,aAccessToken));
    }

    public boolean isValid(AppUser aUser, String aAccessToken){
        AccessTicket foundTicket = null;

        for(AccessTicket ticket : accessTickets){
            if(aUser.getName().equals(ticket.getUser().getName())){
                foundTicket = ticket;
                break;
            }
        }

        if(foundTicket == null){
            return false;
        }

        if(!userIsAlreadyActive(aUser)){
            return false;
        }

        if(istTicketAbgelaufen(foundTicket)){
            return false;
        }

        if(!isValidAccessToken(aAccessToken, foundTicket)){
            return false;
        }

        return false;
    }

    private boolean userIsAlreadyActive(AppUser aUser) {
        return aUser.isActivated();
    }

    private boolean istTicketAbgelaufen(AccessTicket aTicket) {
        return false;
    }

    private boolean isValidAccessToken(String aAccessToken, AccessTicket aTicket) {
        return aAccessToken.equals(aTicket.getAccessToken());
    }

    private AccessTicket getAccessTicket(AppUser aAppUser) throws AccessTicketNotFoundException {
        for(AccessTicket ticket : accessTickets){
            if(aAppUser.getName().equals(ticket.getUser().getName())){
                return ticket;
            }
        }
        throw new AccessTicketNotFoundException("Dont found any AccessTickets for user: " + aAppUser.toString());
    }

}
