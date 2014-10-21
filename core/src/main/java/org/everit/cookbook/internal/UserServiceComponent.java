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

import java.util.Objects;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.everit.cookbook.UserService;
import org.everit.cookbook.dto.UserDTO;
import org.everit.cookbook.schema.qdsl.QUser;
import org.everit.osgi.querydsl.support.QuerydslSupport;

import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QBean;

@Component(name = "org.everit.cookbook.UserService", configurationFactory = true, metatype = true,
        policy = ConfigurationPolicy.REQUIRE)
@Properties({
        @Property(name = "querydslSupport.target"),
        @Property(name = "service.description", propertyPrivate = false) })
@Service
public class UserServiceComponent implements UserService {

    @Reference(name = "querydslSupport", bind = "setQdsl")
    private QuerydslSupport qdsl;

    @Override
    public long createUser(String firstName, String lastName) {
        Objects.requireNonNull(firstName, "firstName must not be null");
        Objects.requireNonNull(lastName, "lastName must not be null");

        return qdsl.execute((connection, configuration) -> {
            QUser user = QUser.user;
            SQLInsertClause insert = new SQLInsertClause(connection, configuration, user);

            return insert
                    .set(user.firstName, firstName)
                    .set(user.lastName, lastName)
                    .executeWithKey(user.userId);
        });
    }

    @Override
    public UserDTO getUserById(long userId) {
        return qdsl.execute((connection, configuration) -> {
            QUser user = QUser.user;
            SQLQuery query = new SQLQuery(connection, configuration);

            QBean<UserDTO> userQBean = new QBean<UserDTO>(UserDTO.class, true, user.userId, user.firstName,
                    user.lastName);

            return query
                    .from(user)
                    .where(user.userId.eq(userId))
                    .singleResult(
                            userQBean);
        });
    }

    public void setQdsl(QuerydslSupport qdsl) {
        this.qdsl = qdsl;
    }
}
