package org.everit.cookbook.tests;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.everit.cookbook.CreateUserParameter;
import org.everit.cookbook.UserDTO;
import org.everit.cookbook.UserService;
import org.everit.osgi.dev.testrunner.TestDuringDevelopment;
import org.junit.Assert;
import org.junit.Test;

@Component
@Service(UserServiceTest.class)
@Properties({
        @Property(name = "eosgi.testEngine", value = "junit4"),
        @Property(name = "eosgi.testId", value = "UserServiceTest")
})
@TestDuringDevelopment
public class UserServiceTest {

    @Reference(bind = "setUserService")
    private UserService userService;

    protected void setUserService(final UserService userService) {
        this.userService = userService;
    }

    @Test
    public void testCreateAndGetUser() {
        long userId = userService.createUser(new CreateUserParameter().firstName("John").lastName("Doe"));
        UserDTO user = userService.getUserById(userId);

        Assert.assertNotNull(user);
        Assert.assertEquals(userId, user.userId);
        Assert.assertEquals("John", user.firstName);
        Assert.assertEquals("Doe", user.lastName);
    }
    
    @Test(expected = NullPointerException.class)
    public void testCreateUserParamNull() {
        userService.createUser(null);
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
