package org.everit.cookbook;

import org.everit.cookbook.dto.UserDTO;

public interface UserService {

    /**
     * Creates a new user.
     * 
     * @param firstName
     *            The first name of the user. Must not be null.
     * @param lastName
     *            The last name of the user. Must not be null.
     * @return The id of the newly created user.
     * @throws NullPointerException
     *             if any of the parameter is null.
     */
    long createUser(String firstName, String lastName);

    /**
     * Queries the data of a user.
     * 
     * @param userId
     *            The id of the user.
     * @return The data of the user or <code>null</code> if there is no user with the specified id.
     */
    UserDTO getUserById(long userId);
}
