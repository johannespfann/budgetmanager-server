package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.model.StandingOrder;
import de.pfann.budgetmanager.server.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("admin/")
public class AdminResource {

    private AdminResourceFacade adminResourceFacade;

    public AdminResource(AdminResourceFacade aAdminResourceFacade) {
        adminResourceFacade = aAdminResourceFacade;
    }

    @GET
    @Path("users")
    public List<User> allUsers() {
        List<User> users = adminResourceFacade.allUsers();
        return users;
    }

    @GET
    @Path("users/{user}/accounts")
    public List<Account> allAccountsByUser(@PathParam("owner") String aOwner) {
        List<Account> accounts = adminResourceFacade.allAccountsByUser(aOwner);
        return accounts;
    }

    @GET
    @Path("users/{user}/accounts/{account}/entries")
    public List<Entry> allEntriesByAccount(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccount) {
        List<Entry> entries = adminResourceFacade.allEntriesByUserAndAccount(aOwner, aAccount);
        return entries;
    }

    @GET
    @Path("users/{user}/accounts/{account}/standingorders")
    public List<StandingOrder> allStandingOrdersByAccount(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccount) {
        List<StandingOrder> entries = adminResourceFacade.allStandingOrdersByUserAndAccount(aOwner, aAccount);
        return entries;
    }

}
