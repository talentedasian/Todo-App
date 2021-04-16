package com.example.forum_4_stupid.twillio;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Configuration
public class TwillioConfig {
	
	private static final String TWILLIO_AUTH_TOKEN = "b49fd7c02f4dc2b7c76f62c36566cdc2";
	private static final String TWILLIO_ACCOUNT_SID = "AC7aaeb5f341d85832a64127ec9ac815c1";

	@Bean
	public String twillioPhoneNumber(){
		return "+19412144325";
	}
	
	@PostConstruct
	public void sendMessage() {
		String ACCOUNT_SID = "AC7aaeb5f341d85832a64127ec9ac815c1";
		String AUTH_TOKEN = "b49fd7c02f4dc2b7c76f62c36566cdc2";
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

	}
	
}
