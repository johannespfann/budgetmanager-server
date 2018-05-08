package de.pfann.budgetmanager.server.restservices.resources.login;

import java.util.ArrayList;
import java.util.List;

// simple singleton -
// TODO implement singleton threatsafe
public class ActivationPool {

    private static ActivationPool instance;

    // TODO Not threatsafe
    private List<ActivationTicket> ticketPool;

    private ActivationPool(){
        ticketPool = new ArrayList<>();
    }

    public static ActivationPool create(){
        if(instance == null){
            instance = new ActivationPool();
        }
        return instance;
    }

    public boolean codeExists(String aCode){
        for(ActivationTicket ticket : ticketPool){
            if( ticket.equals(aCode)){
                return true;
            }
        }
        return false;
    }

    public ActivationTicket getActivationTicket(String aCode) throws ActivationTicketNotFoundException{
        // TODO delete showPool() + Method
        showPool();
        for(ActivationTicket ticket : ticketPool){
            if( ticket.getActivationCode().equals(aCode)){
                return ticket;
            }
        }
        throw new ActivationTicketNotFoundException("Could not found Ticket with Code: " + aCode);
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
