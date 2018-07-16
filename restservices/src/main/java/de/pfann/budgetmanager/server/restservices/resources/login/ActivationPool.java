package de.pfann.budgetmanager.server.restservices.resources.login;

import java.util.ArrayList;
import java.util.List;

// simple singleton -
// TODO implement singleton threatsafe
public class ActivationPool {

    // TODO Not threatsafe
    private List<ActivationTicket> ticketPool;

    public ActivationPool(){
        ticketPool = new ArrayList<>();
    }

    public boolean codeExists(String aCode){
        System.out.println("-> All entries of pool!");
        showPool();
        for(ActivationTicket ticket : ticketPool){
            if( ticket.getActivationCode().equals(aCode)){
                System.out.println("compare ticket.code: " + ticket.getActivationCode() + " and incomming code: " + aCode)  ;
                return true;
            }
        }
        return false;
    }


    public ActivationTicket getActivationTicket(String aCode){
        showPool();
        for(ActivationTicket ticket : ticketPool){
            if( ticket.getActivationCode().equals(aCode)){
                return ticket;
            }
        }
        return null;
    }

    public void addActivationTicket(String aUsername, String aEmail, String aCode) throws ActivationCodeAlreadyExistsException{
        ActivationTicket ticket = new ActivationTicket(aUsername,aCode,aEmail);
        if(codeExists(aCode)){
            throw new ActivationCodeAlreadyExistsException("ActivationCode already exists: " + aCode);
        }
        ticketPool.add(ticket);
        // TODO Delete show Pool
        showPool();
    }

    private void showPool(){
        for(ActivationTicket ticket : ticketPool){
            System.out.println(ticket.toString());
        }
    }




}
