package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("contact/")
public class ContactResource {

    private ContactResourceFacade contactResourceFacade;

    public ContactResource(final ContactResourceFacade aResourceFacade){
        contactResourceFacade = aResourceFacade;
    }

    @POST
    @Logged
    @CrossOriginFilter
    @Path("send")
    public void sendContactMessage(String aBody){
        final ContactMessage contactMessage = ContactMessageMapper.convertToContectMessage(aBody);
        contactResourceFacade.sendEmail(contactMessage.getEmail(),contactMessage.getName(),contactMessage.getMessage());
    }

}
