package com.notification.challenge.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.notification.challenge.controllers.UserResponse;

@Repository
public class UserRepositoryImpl implements UserRepository {

	private List<UserResponse> registeredUsers = new ArrayList<>();
	
	public void registerUser(UserResponse user) {
		registeredUsers.add(user);
	}

	public List<UserResponse> getUsers() {
		return registeredUsers;
	}

	public UserResponse getUser(String username) {
		return registeredUsers.stream()
				.filter(u -> u.getUsername().equalsIgnoreCase(username))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Username does not exist"));
	}

	public boolean exists(String username) {
		return registeredUsers.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
	}

}
