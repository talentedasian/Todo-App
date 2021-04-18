package com.example.forum_4_stupid.twillio;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

@Configuration
public class TwillioUtilitySmsMessager {
	

	private static final String TWILLIO_AUTH_TOKEN = "b49fd7c02f4dc2b7c76f62c36566cdc2";
	private static final String TWILLIO_ACCOUNT_SID = "AC7aaeb5f341d85832a64127ec9ac815c1";
	
	@Value("${twillio.phone.number}")
	private String phoneNumber;
	
	@Bean
	public PhoneNumber number() {
		return new PhoneNumber(phoneNumber);
	}
	
	@PostConstruct
	public void initializeTwillio() {
		Twilio.init(TWILLIO_ACCOUNT_SID, TWILLIO_AUTH_TOKEN);
		
	}

//	@Autowired
//	private static PhoneNumber twillioPhoneNumber;
//	
//	private final static String message = "";
//	
//	public static MessageCreator createSmsMessage(PhoneNumber to) {
//		MessageCreator messageToBeSent = new MessageCreator(to, twillioPhoneNumber, message);
//		
//		return messageToBeSent;
//	}
//	
//	public static void sendSmsMessageAsync(MessageCreator messageToBeSent) {
//		messageToBeSent.createAsync();
//	}
//	
//	public static void sendSmsMessage(MessageCreator messageToBeSent) {
//		messageToBeSent.create();
//	}
	
}
