package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;
import de.pfann.budgetmanager.server.restservices.resources.filter.ValidateContactRequest;
import de.pfann.budgetmanager.server.restservices.resources.util.EmailValidator;

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
    @ValidateContactRequest
    @Path("send")
    public void sendContactMessage(String aBody){
        final ContactMessage contactMessage = ContactMessageMapper.convertToContectMessage(aBody);
        EmailValidator.assertEmailIsValid(contactMessage.getEmail());
        assertMessage(contactMessage.getName());
        assertName(contactMessage.getMessage());
        contactResourceFacade.sendEmail(contactMessage.getEmail(),contactMessage.getName(),contactMessage.getMessage());
    }

    private void assertName(String aName) {
        if(aName == null || aName.isEmpty()){
            throw new IllegalArgumentException("Falsches Argument fuer Name: " + aName);
        }
    }

    private void assertMessage(String aMessage) {
        if(aMessage == null || aMessage.isEmpty()){
            throw new IllegalArgumentException("Falsches Argument fuer Nachricht: " + aMessage);
        }
    }

}
