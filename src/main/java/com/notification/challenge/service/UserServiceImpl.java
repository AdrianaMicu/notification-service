package com.notification.challenge.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notification.challenge.controllers.UserResponse;
import com.notification.challenge.dao.UserRepository;
import com.notification.challenge.exceptions.UserRegistrationException;
import com.notification.challenge.service.push.PushNotificationService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PushNotificationService pushService;
	
	public UserResponse registerUser(String username, String accessToken) {
		if (userRepo.exists(username)) {
			throw new UserRegistrationException("User is already registered");
		}
		
		String email = pushService.getUser(accessToken).orElseThrow(() -> new UserRegistrationException("Could not register user - unauthorised - invalid access token")).getEmail();

		UserResponse userResponse = new UserResponse();
		userResponse.setUsername(username);
		userResponse.setAccessToken(accessToken);
		userResponse.setCreationTime(new Date());
		userResponse.setNumOfNotificationsPushed(new AtomicLong(0));
		userResponse.setEmail(email);
		
		userRepo.registerUser(userResponse);
		
		return userResponse;
	}

	public List<UserResponse> getUsers() {
		return userRepo.getUsers();
	}

}
