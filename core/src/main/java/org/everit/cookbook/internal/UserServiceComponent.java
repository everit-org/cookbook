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
package org.everit.cookbook.internal;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.everit.cookbook.UserService;
import org.everit.cookbook.dto.UserDTO;

@Component
@Service
public class UserServiceComponent implements UserService {

    private final Map<Long, UserDTO> storage = new ConcurrentHashMap<Long, UserDTO>();

    private final AtomicLong lastGeneratedUserId = new AtomicLong();

    @Override
    public long createUser(String firstName, String lastName) {
        Objects.requireNonNull(firstName, "firstName must not be null");
        Objects.requireNonNull(lastName, "lastName must not be null");

        long userId = lastGeneratedUserId.get();
        UserDTO userData = new UserDTO(userId, firstName, lastName);
        storage.put(userId, userData);
        return userId;
    }

    @Override
    public UserDTO getUserById(long userId) {
        return storage.get(userId);
    }

}
