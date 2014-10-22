/**
 * This file is part of org.everit.cookbook.core.
 *
 * org.everit.cookbook.core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * org.everit.cookbook.core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with org.everit.cookbook.core.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.everit.cookbook;

public interface UserService {

    /**
     * Creates a new user.
     *
     * @param parameterObject
     *            TODO
     *
     * @return The id of the newly created user.
     * @throws NullPointerException
     *             if any of the parameter is null.
     */
    long createUser(CreateUserParameter parameterObject);

    /**
     * Queries the data of a user.
     *
     * @param userId
     *            The id of the user.
     * @return The data of the user or <code>null</code> if there is no user with the specified id.
     */
    UserDTO getUserById(long userId);
}
