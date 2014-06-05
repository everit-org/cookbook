package org.everit.cookbook.tests;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.junit.Test;

@Component
@Service(UserServiceTest.class)
@Properties({
        @Property(name = "eosgi.testEngine", value = "junit4"),
        @Property(name = "eosgi.testId", value = "UserServiceTest")
})
public class UserServiceTest {

    @Test
    public void testUserCreation() {
        System.out.println("Hello world");
    }
}
