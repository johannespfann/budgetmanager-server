package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.common.model.AppUser;

import java.util.List;

public interface AppUserFacade {
    void createNewUser(AppUser aUser);

    void activateUser(AppUser aUser);

    void deactivateUser(AppUser aUser);

    void deleteUser(AppUser aUser);

    AppUser getUserByNameOrEmail(String aIdentifier);

    void updateUser(AppUser aAppUser);

    List<AppUser> getAllUser();
}
