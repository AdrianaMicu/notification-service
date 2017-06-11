package com.notification.challenge.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.notification.challenge.controllers.UserRequest;
import com.notification.challenge.controllers.UserResponse;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryImplTest {

	@InjectMocks
	private UserRepositoryImpl userRepo; 	
	
	private UserRequest userReq1;
	private UserRequest userReq2;
	
	private UserResponse user1;
	private UserResponse user2;
	
	@Before
	public void setup() {
		userReq1 = new UserRequest();
		userReq1.setAccessToken("accessToken1");
		userReq1.setUsername("user1");
		
		userReq2 = new UserRequest();
		userReq2.setAccessToken("accessToken2");
		userReq2.setUsername("user2");
		
		user1 = new UserResponse();
		user1.setAccessToken("accessToken1");
		user1.setCreationTime(new Date());
		user1.setEmail("email1@domain.com");
		user1.setNumOfNotificationsPushed(new AtomicLong(0));
		user1.setUsername("user1");
		
		user2 = new UserResponse();
		user2.setAccessToken("accessToken2");
		user2.setCreationTime(new Date());
		user2.setEmail("email2@domain.com");
		user2.setNumOfNotificationsPushed(new AtomicLong(0));
		user2.setUsername("user2");
	}
	
	@Test
	public void getUser_shouldSucceed() {
		userRepo.registerUser(user1);
		userRepo.registerUser(user2);
		
		UserResponse userResponse1 = userRepo.getUser("user1");
		UserResponse userResponse2 = userRepo.getUser("user2");
		
		assertUser(user1, userResponse1);
		assertUser(user2, userResponse2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getUser_shouldThrowException() {
		userRepo.registerUser(user1);
		userRepo.getUser("user2");
	}
	
	private void assertUser(UserResponse expected, UserResponse actual) {
		assertEquals(expected.getAccessToken(), actual.getAccessToken());
		assertEquals(expected.getCreationTime(), actual.getCreationTime());
		assertEquals(expected.getEmail(), actual.getEmail());
		assertEquals(expected.getNumOfNotificationsPushed(), actual.getNumOfNotificationsPushed());
	}
}
