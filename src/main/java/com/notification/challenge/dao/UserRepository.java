package com.notification.challenge.dao;

import java.util.List;

import com.notification.challenge.controllers.UserResponse;

public interface UserRepository {

	public void registerUser(UserResponse user);

	public List<UserResponse> getUsers();
	
	public UserResponse getUser(String username);

	public boolean exists(String username);
	
}
