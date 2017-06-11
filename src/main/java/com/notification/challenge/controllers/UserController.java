package com.notification.challenge.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.notification.challenge.service.UserService;

@RestController
@RequestMapping(value = "/")
public class UserController {

	private static final String USERS_URL = "users";
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = USERS_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
	public UserResponse createUser(@Valid @RequestBody UserRequest registerUserRequest) {
		return userService.registerUser(registerUserRequest.getUsername(), registerUserRequest.getAccessToken());
	}
	
	@RequestMapping(value = USERS_URL, method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<UserResponse> getUsers() {
		return userService.getUsers();
	}
}
