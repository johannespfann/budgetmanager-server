package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.facade.EntryFacade;
import de.pfann.budgetmanager.server.common.facade.StandingOrderFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;

public class JohannesUseCase extends AbstractUseCase {


    public JohannesUseCase(AppUserFacade aUserFacade, EntryFacade aEntryFacade, StandingOrderFacade aStandingOrderFacade) {
        super(aUserFacade, aEntryFacade, aStandingOrderFacade);
    }

    @Override
    public AppUser createUser() {
        AppUser user = new AppUser();
        user.setName("src/main/resources/johannes-1234");
        user.setPassword("key");
        user.setEncrypted(false);
        user.setEncryptionText("");
        user.setEmail("jopfann@googlemail.com");
        return user;
    }

    public void createContent(AppUser user) {
        userFacade.createNewUser(user);

        Entry entryWaschmaschine = EntryBuilder.createBuilder(user)
                .withAmount("-450")
                .withMemo("Einmalzahlung für Waschmaschine")
                .withTag("Haushalt")
                .build();

        entryFacade.persistEntry(entryWaschmaschine);

        Entry netflixEinmalzahlung = EntryBuilder.createBuilder(user)
                .withAmount("-13")
                .withMemo("Einmalzahlung für Netflix pro Monat")
                .withTag("luxus")
                .withTag("freizeit")
                .build();

        entryFacade.persistEntry(netflixEinmalzahlung);

        Entry eov = EntryBuilder.createBuilder(user)
                .withAmount("2500")
                .withMemo("Datev Zusatzgehalt")
                .withTag("einmalzahlung")
                .withTag("gehalt")
                .build();

        entryFacade.persistEntry(eov);


    }

}
