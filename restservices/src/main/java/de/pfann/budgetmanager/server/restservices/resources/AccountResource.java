package de.pfann.budgetmanager.server.restservices.resources;


import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;
import de.pfann.budgetmanager.server.restservices.resources.core.Secured;
import de.pfann.budgetmanager.server.restservices.resources.util.AccountMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("account/")
public class AccountResource {

    private AccountResourceFacade accountResourceFacade;

    public AccountResource(AccountResourceFacade facade) {
        accountResourceFacade = facade;
    }

    @GET
    @Logged
    @Secured
    @CrossOriginFilter
    @Produces(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/all")
    public String getAccounts(@PathParam("owner") String aOwner) {
        System.out.println("Get Call: " + aOwner);
        List<Account> kontos = accountResourceFacade.getAccounts(aOwner);
        String result = AccountMapper.convertToJson(kontos);
        System.out.println("Result of Call: " + result);
        return result;
    }

    @POST
    @Logged
    @Secured
    @CrossOriginFilter
    @Produces(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/add")
    public void addAccount(@PathParam("owner") String aOwner, String aBody) {
        System.out.println("Get AddAccount: " + aOwner + " with " + aBody);
        Account account = AccountMapper.convertToAccount(aBody);
        accountResourceFacade.addAccount(aOwner, account);
    }

    @DELETE
    @Logged
    @Secured
    @CrossOriginFilter
    @Path("owner/{owner}/delete/{hash}")
    public void deleteAccount(
            @PathParam("owner") String aOwner,
            @PathParam("hash") String aAccountHash) {
        System.out.println("Delete Account: " + aOwner + " with " + aAccountHash);
        accountResourceFacade.deleteAccount(aOwner, aAccountHash);
    }
}
