package com.notification.challenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notification.challenge.controllers.NotificationRequest;
import com.notification.challenge.controllers.NotificationResponse;
import com.notification.challenge.controllers.UserResponse;
import com.notification.challenge.dao.UserRepository;
import com.notification.challenge.exceptions.NotificationException;
import com.notification.challenge.mappers.NotificationResponseMapper;
import com.notification.challenge.service.push.Push;
import com.notification.challenge.service.push.PushNotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PushNotificationService pushService;
	
	@Autowired 
	private NotificationResponseMapper notificationResponseMapper;
	
	@Override
	public NotificationResponse sendNotification(String username, NotificationRequest notificationRequest) {
		UserResponse user = userRepo.getUser(username);
		
		Push push = pushService.sendPushNotification(user.getEmail(), notificationRequest).orElseThrow(() -> new NotificationException("Could not send push notification"));
		user.getNumOfNotificationsPushed().incrementAndGet();
		return notificationResponseMapper.toNotificationResponse(push);
	}

}
