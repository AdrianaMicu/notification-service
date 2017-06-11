package com.notification.challenge.service;


import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.notification.challenge.dao.UserRepository;
import com.notification.challenge.exceptions.UserRegistrationException;
import com.notification.challenge.service.push.PushNotificationService;
import com.notification.challenge.service.push.PushUser;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock
	private UserRepository userRepo;
	
	@Mock
	private PushNotificationService pushService;
	
	@Test
	public void registerUser_shouldSucceed() {
		when(userRepo.exists(Mockito.anyString())).thenReturn(false);
		when(pushService.getUser(Mockito.anyString())).thenReturn(Optional.of(mock(PushUser.class)));
		
		userService.registerUser("username", "accessToken");
		
		verify(userRepo).registerUser(Mockito.any());
	}
	
	@Test
	public void registerUser_shouldThrowError() {
		when(userRepo.exists(Mockito.anyString())).thenReturn(true);
		
		Throwable throwable = catchThrowable(() -> userService.registerUser("username", "accessToken"));
		
		assertThat(throwable).isNotNull();
		assertThat(throwable).isInstanceOf(UserRegistrationException.class);
		assertEquals("User is already registered", throwable.getMessage());
		verifyZeroInteractions(pushService);
		verify(userRepo).exists(Mockito.anyString());
		verifyNoMoreInteractions(userRepo);
	}
}
