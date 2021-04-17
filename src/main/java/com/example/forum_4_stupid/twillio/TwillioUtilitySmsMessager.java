package com.example.forum_4_stupid.twillio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

@Component
public class TwillioUtilitySmsMessager {

	@Autowired
	private static PhoneNumber twillioPhoneNumber;
	
	private final static String message = "";
	
	public static MessageCreator createSmsMessage(PhoneNumber to) {
		MessageCreator messageToBeSent = new MessageCreator(to, twillioPhoneNumber, message);
		
		return messageToBeSent;
	}
	
	public static void sendSmsMessageAsync(MessageCreator messageToBeSent) {
		messageToBeSent.createAsync();
	}
	
	public static void sendSmsMessage(MessageCreator messageToBeSent) {
		messageToBeSent.create();
	}
	
}
