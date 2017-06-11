package com.notification.challenge.service.push;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.notification.challenge.controllers.NotificationRequest;
import com.notification.challenge.mappers.PushNotificationMapper;

@Service
public class PushNotificationServiceImpl implements PushNotificationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PushNotificationServiceImpl.class);
	
	@Value("${pushbullet.baseurl}")
	private String pushBulletBaseUrl;
	
	@Value("${pushbullet.accesstoken}")
	private String apiAccessToken;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PushNotificationMapper pushNotificationMapper;
	
	@Override
	public Optional<Push> sendPushNotification(String email, NotificationRequest notificationRequest) {
		PushNotification pushNotification = pushNotificationMapper.toPushNotification(email, notificationRequest);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Token", apiAccessToken);
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity<PushNotification> createPushEntity = new HttpEntity<>(pushNotification, headers);

		ResponseEntity<Push> pushResponse;
		try {
			pushResponse = restTemplate.exchange(pushBulletBaseUrl + "pushes", HttpMethod.POST, createPushEntity, Push.class);
			return Optional.of(pushResponse.getBody());
		} catch (HttpClientErrorException e) {
			LOGGER.error("Could not send push notification: ", e);
			return Optional.empty();
		}
	}

	@Override
	public Optional<PushUser> getUser(String accessToken) {
	    HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Token", accessToken);
		
		ResponseEntity<PushUser> userResponse;
		try {
			userResponse = restTemplate.exchange(pushBulletBaseUrl + "users/me", HttpMethod.GET, new HttpEntity<>(null, headers), PushUser.class);
			return Optional.of(userResponse.getBody());
		} catch (HttpClientErrorException e) {
			LOGGER.error("Could not retrieve user: ", e);
			return Optional.empty();
		}
	}
}
