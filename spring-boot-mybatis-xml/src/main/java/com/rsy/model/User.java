package com.rsy.model;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	private boolean role;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(name, user.name) && Objects.equals(password, user.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, password);
	}
}