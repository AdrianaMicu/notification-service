package com.notification.challenge.service;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.notification.challenge.controllers.NotificationRequest;
import com.notification.challenge.controllers.UserResponse;
import com.notification.challenge.dao.UserRepository;
import com.notification.challenge.exceptions.NotificationException;
import com.notification.challenge.mappers.NotificationResponseMapper;
import com.notification.challenge.service.push.Push;
import com.notification.challenge.service.push.PushNotificationService;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceImplTest {

	@InjectMocks
	private NotificationServiceImpl notificationService;
	
	@Mock
	private UserRepository userRepo;
	
	@Mock
	private PushNotificationService pushService;
	
	@Mock
	private NotificationResponseMapper notificationResponseMapper;
	
	@Test
	public void sendNotification_shouldSucceed() {
		UserResponse user = mock(UserResponse.class);
		when(userRepo.getUser(Mockito.anyString())).thenReturn(user);
		when(user.getEmail()).thenReturn("email@domain.com");
		when(user.getNumOfNotificationsPushed()).thenReturn(new AtomicLong(0));
		Push push = mock(Push.class);
		when(pushService.sendPushNotification(Mockito.anyString(), Mockito.any())).thenReturn(Optional.of(push));
		
		notificationService.sendNotification("user1", mock(NotificationRequest.class));
		
		verify(notificationResponseMapper).toNotificationResponse(push);
		assertEquals(new AtomicLong(1).get(), user.getNumOfNotificationsPushed().get());
	}
	
	@Test
	public void sendNotification_shouldThrowError() {
		UserResponse user = mock(UserResponse.class);
		when(userRepo.getUser(Mockito.anyString())).thenReturn(user);
		when(user.getEmail()).thenReturn("email@domain.com");
		when(user.getNumOfNotificationsPushed()).thenReturn(new AtomicLong(0));
		when(pushService.sendPushNotification(Mockito.anyString(), Mockito.any())).thenReturn(Optional.empty());
		
		Throwable throwable = catchThrowable(() -> notificationService.sendNotification("user1", mock(NotificationRequest.class)));
		
		assertThat(throwable).isNotNull();
		assertThat(throwable).isInstanceOf(NotificationException.class);
		assertEquals("Could not send push notification", throwable.getMessage());
		verifyZeroInteractions(notificationResponseMapper);
		assertEquals(new AtomicLong(0).get(), user.getNumOfNotificationsPushed().get());
	}
}
