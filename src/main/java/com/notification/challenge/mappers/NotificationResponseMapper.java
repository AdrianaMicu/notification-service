package com.notification.challenge.mappers;

import org.springframework.stereotype.Component;

import com.notification.challenge.controllers.NotificationResponse;
import com.notification.challenge.service.push.Push;

@Component
public class NotificationResponseMapper {

	public NotificationResponse toNotificationResponse(Push push) {
		NotificationResponse notificationResponse = new NotificationResponse();
		notificationResponse.setBody(push.getBody());
		notificationResponse.setTitle(push.getTitle());
		notificationResponse.setSenderEmail(push.getSender_email());
		notificationResponse.setReceiverEmail(push.getReceiver_email());
		
		return notificationResponse;
	}
}
