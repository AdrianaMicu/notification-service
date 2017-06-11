package com.notification.challenge.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notification.challenge.service.NotificationService;

@RestController
@RequestMapping(value = "/")
public class NotificationController {
	
	private static final String NOTIFICATIONS_URL = "notifications";
	
	@Autowired
	private NotificationService notificationService;
	
	@RequestMapping(value = NOTIFICATIONS_URL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public NotificationResponse sendNotification(
			@RequestParam(required = true) String username,
			@Valid @RequestBody NotificationRequest notificationRequest) {
		return notificationService.sendNotification(username, notificationRequest);
	}
}
