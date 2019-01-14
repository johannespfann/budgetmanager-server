package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.model.User;

import java.util.List;

public interface UserFacade {

    /**
     * Create a new User without any konto.
     * @param aUser
     */
    void createNewUser(User aUser);


    /**
     * aktiviate new user
     * @param aUser
     */
    void activateUser(User aUser);

    /**
     * deativate user
     * @param aUser
     */
    void deactivateUser(User aUser);

    /**
     * delete user and all data belongs to this user
     * @param aUser
     */
    void deleteUser(User aUser);

    /**
     * find user by his email or if not found for with his username. Both of them are unique
     * @param aIdentifier
     * @return
     */
    User getUserByNameOrEmail(String aIdentifier);

    /**
     * update a existing user
     * @param aAppUser
     */
    void updateUser(User aAppUser);

    /**
     * get all user of the application
     * @return
     */
    List<User> getAllUser();

}
