package org.everit.cookbook.dto;

import java.io.Serializable;

public final class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    protected long userId;

    protected String firstName;

    protected String lastName;
    
    protected UserDTO() {
    }

    public UserDTO(long userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
