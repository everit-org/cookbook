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
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.everit.cookbook.CreateUserParameter;
import org.everit.cookbook.ImmutableUser;
import org.everit.cookbook.UserDTO;
import org.everit.cookbook.UserService;

@Component(name = "org.everit.cookbook.UserService", configurationFactory = true, metatype = true,
        policy = ConfigurationPolicy.REQUIRE)
@Properties({ @Property(name = "service.description", propertyPrivate = false) })
@Service
public class UserServiceComponent implements UserService {

    private final Map<Long, ImmutableUser> storage = new ConcurrentHashMap<Long, ImmutableUser>();

    private final AtomicLong lastGeneratedUserId = new AtomicLong();

    @Override
    public long createUser(CreateUserParameter newUserParam) {
        Objects.requireNonNull(newUserParam, "User parameter cannot be null");
        Objects.requireNonNull(newUserParam.firstName, "firstName must not be null");
        Objects.requireNonNull(newUserParam.lastName, "lastName must not be null");

        long userId = lastGeneratedUserId.get();
        UserDTO userDTO = new UserDTO().userId(userId).firstName(newUserParam.firstName)
                .lastName(newUserParam.lastName);

        ImmutableUser immutableUser = new ImmutableUser(userDTO);
        storage.put(userId, immutableUser);
        return userId;
    }

    @Override
    public UserDTO getUserById(long userId) {
        ImmutableUser immutableUser = storage.get(userId);
        if (immutableUser == null) {
            return null;
        }

        return new UserDTO(immutableUser);
    }

}
