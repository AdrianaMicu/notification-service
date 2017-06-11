package com.notification.challenge.service;

import java.util.List;

import com.notification.challenge.controllers.UserResponse;
import com.notification.challenge.exceptions.UserRegistrationException;

public interface UserService {

	public UserResponse registerUser(String username, String accessToken) throws UserRegistrationException;

	public List<UserResponse> getUsers();
}
