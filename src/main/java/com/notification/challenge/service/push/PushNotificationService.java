package com.notification.challenge.service.push;

import java.util.Optional;

import com.notification.challenge.controllers.NotificationRequest;

public interface PushNotificationService {

	public Optional<Push> sendPushNotification(String email, NotificationRequest notificationRequest);
	
	public Optional<PushUser> getUser(String accessToken);
}
