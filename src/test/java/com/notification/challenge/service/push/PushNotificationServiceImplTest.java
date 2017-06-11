package com.notification.challenge.service.push;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.notification.challenge.controllers.NotificationRequest;
import com.notification.challenge.mappers.PushNotificationMapper;

@RunWith(MockitoJUnitRunner.class)
public class PushNotificationServiceImplTest {

	private static final String PUSHBULLET_URL = "https://api.pushbullet.com/v2/";
	private static final String ACCESS_TOKEN = "o.YFGlYDrovm9nnPAePpKxduc6zqg1OPD3";
	
	@InjectMocks
	PushNotificationServiceImpl pushNotificationService;

	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private PushNotificationMapper pushNotificationMapper;
	
	@Captor ArgumentCaptor<HttpEntity> entityCaptor;

	@Before
	public void setup() {
		ReflectionTestUtils.setField(pushNotificationService, "pushBulletBaseUrl", PUSHBULLET_URL);
		ReflectionTestUtils.setField(pushNotificationService, "apiAccessToken", ACCESS_TOKEN);
	}
	
	@Test
	public void sendPushNotification_shouldSucceed() {
		String title = "Notification Test Title";
		String body = "Notification Test Body";
		NotificationRequest.Type type = NotificationRequest.Type.NOTE;
		
		PushNotification pushNotification = new PushNotification(); 
		pushNotification.setTitle(title);
		pushNotification.setBody(body);
		pushNotification.setType(type.name());
		
		when(pushNotificationMapper.toPushNotification(Mockito.anyString(), Mockito.any())).thenReturn(pushNotification);
		ResponseEntity<Push> pushResponse = ResponseEntity.ok(mock(Push.class));
		when(restTemplate.exchange(Mockito.anyString(), eq(HttpMethod.POST), Mockito.any(HttpEntity.class), eq(Push.class))).thenReturn(pushResponse);
		
		NotificationRequest notificationRequest = mock(NotificationRequest.class);
		when(notificationRequest.getTitle()).thenReturn(title);
		when(notificationRequest.getBody()).thenReturn(body);
		when(notificationRequest.getType()).thenReturn(type);
		
		pushNotificationService.sendPushNotification("email", notificationRequest);
		
		verify(restTemplate).exchange(eq("https://api.pushbullet.com/v2/pushes"), eq(HttpMethod.POST), entityCaptor.capture(), eq(Push.class));
		
		assertThat(entityCaptor.getValue().getHeaders().get("Access-Token")).isEqualTo(Lists.newArrayList("o.YFGlYDrovm9nnPAePpKxduc6zqg1OPD3"));
		
		Object requestBody = entityCaptor.getValue().getBody();
		assertThat((String)ReflectionTestUtils.getField(requestBody, "title")).isEqualTo(title);
		assertThat((String)ReflectionTestUtils.getField(requestBody, "body")).isEqualTo(body);
		assertThat((String)ReflectionTestUtils.getField(requestBody, "type")).isEqualTo(type.name());
	}
}
