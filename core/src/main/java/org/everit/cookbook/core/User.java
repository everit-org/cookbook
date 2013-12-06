package org.everit.cookbook.core;

import java.util.Date;

public class User {

	private long userId;

	private String name;

	private Date birthDate;

	public Date getBirthDate() {
		return birthDate;
	}

	public String getName() {
		return name;
	}

	public long getUserId() {
		return userId;
	}

	public void setBirthDate(final Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setUserId(final long userId) {
		this.userId = userId;
	}
}
