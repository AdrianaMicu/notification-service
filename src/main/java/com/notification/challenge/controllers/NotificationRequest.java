package com.notification.challenge.controllers;

public class NotificationRequest {

	public enum Type {
		NOTE,
		FILE,
		LINK
	}
	
	private String title;
	private String body;
	private Type type;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
}
