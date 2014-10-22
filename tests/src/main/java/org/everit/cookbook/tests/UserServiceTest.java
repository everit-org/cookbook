package org.everit.cookbook.tests;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.everit.cookbook.CreateUserParameter;
import org.everit.cookbook.UserDTO;
import org.everit.cookbook.UserService;
import org.everit.cookbook.schema.qdsl.QUser;
import org.everit.osgi.dev.testrunner.TestDuringDevelopment;
import org.everit.osgi.querydsl.support.QuerydslSupport;
import org.junit.Assert;
import org.junit.Test;

import com.mysema.query.sql.dml.SQLDeleteClause;

@Component(configurationFactory = true, metatype = true, policy = ConfigurationPolicy.REQUIRE)
@Service(UserServiceTest.class)
@Properties({
        @Property(name = "userService.target"),
        @Property(name = "querydslSupport.target"),
        @Property(name = "service.description", propertyPrivate = false),
        @Property(name = "eosgi.testEngine", value = "junit4"),
        @Property(name = "eosgi.testId", value = "UserServiceTest")
})
@TestDuringDevelopment
public class UserServiceTest {

    private class TestDataCleaner implements AutoCloseable {

        @Override
        public void close() {
            qdsl.execute((connection, configuration) -> {
                new SQLDeleteClause(connection, configuration, QUser.user).execute();
                return null;
            });
        }
    }

    @Reference(name = "querydslSupport", bind = "setQuerydslSupport")
    private QuerydslSupport qdsl;

    @Reference(bind = "setUserService")
    private UserService userService;

    protected void setQuerydslSupport(final QuerydslSupport qdsl) {
        this.qdsl = qdsl;
    }

    protected void setUserService(final UserService userService) {
        this.userService = userService;
    }

    @Test
    public void testCreateAndGetUser() {
        try (TestDataCleaner dummy = new TestDataCleaner()) {
            long userId = userService.createUser(new CreateUserParameter().firstName("John").lastName("Doe"));
            UserDTO user = userService.getUserById(userId);

            Assert.assertNotNull(user);
            Assert.assertEquals(userId, user.userId);
            Assert.assertEquals("John", user.firstName);
            Assert.assertEquals("Doe", user.lastName);
        }
    }

    @Test(expected = NullPointerException.class)
    public void testCreateUserFirstNameNull() {
        userService.createUser(new CreateUserParameter().lastName("Doe"));
    }

    @Test(expected = NullPointerException.class)
    public void testCreateUserLastNameNull() {
        userService.createUser(new CreateUserParameter().firstName("John"));
    }
}
