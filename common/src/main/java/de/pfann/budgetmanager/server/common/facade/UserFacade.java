package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.model.User;

import java.util.List;

public interface UserFacade {

    void createNewUser(User aUser);

    void activateUser(User aUser);

    void deactivateUser(User aUser);

    void deleteUser(User aUser);

    User getUserByNameOrEmail(String aIdentifier);

    boolean isEmailAlreadyExists(String aEmail);

    void updateUser(User aAppUser);

    List<User> getAllUser();

}
