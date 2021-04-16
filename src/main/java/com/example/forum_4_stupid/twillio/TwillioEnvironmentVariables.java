package com.example.forum_4_stupid.twillio;

import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

public class TwillioEnvironmentVariables {

		
	public static MessageCreator createSmsMessage(PhoneNumber to, PhoneNumber from, String message) {
		MessageCreator messageToBeSent = new MessageCreator(to, from, message);
		
		
		return messageToBeSent;
	}
	
	public static void sendSmsMessage(MessageCreator messageToBeSent) {
		messageToBeSent.createAsync();
	}
	
}
