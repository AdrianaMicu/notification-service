package com.notification.challenge.controllers;

import org.hibernate.validator.constraints.NotEmpty;

public class UserRequest {

	@NotEmpty(message = "Username is required")
	private String username;
	
	@NotEmpty(message = "Access Token is required")
	private String accessToken;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
