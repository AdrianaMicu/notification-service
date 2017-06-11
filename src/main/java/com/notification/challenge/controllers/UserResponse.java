package com.notification.challenge.controllers;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserResponse {

	private String username;
	private String accessToken;
	private Date creationTime;
	private AtomicLong numOfNotificationsPushed;
	
	@JsonIgnore
	private String email;
	
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
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public AtomicLong getNumOfNotificationsPushed() {
		return numOfNotificationsPushed;
	}
	public void setNumOfNotificationsPushed(AtomicLong numOfNotificationsPushed) {
		this.numOfNotificationsPushed = numOfNotificationsPushed;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
