package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.common.model.AppUser;

public interface UseCase {


    void setState(String aState);

    void init();

    AppUser createUser();

    void createContent(AppUser aUser);

}
