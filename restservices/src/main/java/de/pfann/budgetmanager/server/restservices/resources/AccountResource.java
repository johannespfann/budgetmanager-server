package de.pfann.budgetmanager.server.restservices.resources;


import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;
import de.pfann.budgetmanager.server.restservices.resources.core.Secured;
import de.pfann.budgetmanager.server.restservices.resources.util.AccountMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
}
