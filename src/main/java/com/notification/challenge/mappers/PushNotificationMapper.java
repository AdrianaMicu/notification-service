package com.notification.challenge.mappers;

import org.springframework.stereotype.Component;

import com.notification.challenge.controllers.NotificationRequest;
import com.notification.challenge.service.push.PushNotification;

@Component
public class PushNotificationMapper {

	public PushNotification toPushNotification(String email, NotificationRequest notificationRequest) {
		PushNotification pushNotification = new PushNotification();
		pushNotification.setEmail(email);
		pushNotification.setTitle(notificationRequest.getTitle());
		pushNotification.setBody(notificationRequest.getBody());
		pushNotification.setType(notificationRequest.getType().name().toLowerCase());
		
		return pushNotification;
	}
}
