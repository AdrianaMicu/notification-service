package com.notification.challenge.service;

import com.notification.challenge.controllers.NotificationRequest;
import com.notification.challenge.controllers.NotificationResponse;

public interface NotificationService {

	public NotificationResponse sendNotification(String username, NotificationRequest notificationRequest);
}
