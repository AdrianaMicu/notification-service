# PushBullet integration Notification Service #

This is a **PushBullet** integration notification service wriiten with **Java 8** and using the **Spring-Boot** framework to develop the **REST API** that allows any client to connect to the service.

The build tool used is **Gradle**. In the **build.gradle** file, there are all the dependencies needed for the project.

**Mockito** has been used to help with the unit testing.

**SLF4J** has been used for logging.

# Introduction #

The service REST API allows: user registration, users overview and sending notifications to any of the registered users.

# Prerequisites #

To be able to use the PushBullet API integrated in this service, we need an access token:
Visit https://www.pushbullet.com/

	1. Sign up
	2. Go to Devices
		a. Install Pushbullet to a device of your choice (be it a phone or a web browser)
	3. Go to Settings, Account
		a. Select Create Access Token
		b. Store the generated access token somewhere safe
		
Have your preffered IDE installed.
Have JDK installed.

# Functionality #

Using basic **curl** syntax or a tool like **Postman** for example, the service supports following requests:

POST request to register a user with the notification service:

http://{hostname}:{port}/notification-service/users

#### Request Body example
	{
		"username": "username1",
		"accessToken": "theAccessToken"
	}

Where **accessToken** represents the PushBullet access token

#### Response Body example
	{
		"username": "username1",
		"accessToken": "theAccessToken",
		"creationTime": "2017-06-11T11:02:52",
		"numOfNotificationsPushed": 0
    }

GET request to retrieve a list of the registered users:

http://{hostname}:{port}/notification-service/users

#### Response Body example
	[
		{
			"username": "username1",
			"accessToken": "theAccessToken",
			"creationTime": "2017-06-11T11:07:20",
			"numOfNotificationsPushed": 2
		},
		{
			"username": "username2",
			"accessToken": "someAccessToken",
			"creationTime": "2017-06-10T11:12:10",
			"numOfNotificationsPushed": 5
		},
		...
	]

POST request to send a notification

http://{hostname}:{port}/notification-service/notifications?username={username}
	
#### Request Body example
	{
		"title": "Hello, from PushBullet Notification Service",
		"body": "You are the lucky receiver of this push notification!",
		"type": "NOTE"
	}

#### Response Body example
	{
		"senderEmail": "sender@domain.com",
		"receiverEmail": "receiver@domain.com",
		"title": "Hello, from PushBullet Notification Service",
		"body": "You are the lucky receiver of this push notification!"
	}
	
	

## Running the service

Make sure you have an IDE installed.
Make sure you have JDK installed.
Clone this project.

You can import it into your IDE and then get the Gradle dependecies and then run the project. Spring Boot will start up Tomcat and will listen by default on the 8080 port. The resources folder contains an **application.properties** file that stores the default port, context path and application name and also some jackson configurations and the PushBullet URL and access tocken (Note that the access token has to be provided).

The project can also be started from the command line using the following command:

	gradle bootRun

## Tests

The tests can be run from the IDE or from the command line using the following command:

	gradle test
