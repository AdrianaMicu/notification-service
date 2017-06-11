package com.notification.challenge.exceptions;

public class UserRegistrationException extends RuntimeException {
	private static final long serialVersionUID = -950330669595756614L;

	public UserRegistrationException(String message) {
		super(message);
	}
}
