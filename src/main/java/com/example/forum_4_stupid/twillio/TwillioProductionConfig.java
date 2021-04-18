package com.example.forum_4_stupid.twillio;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;

@Configuration
@Profile(value = {"development","production"})
public class TwillioProductionConfig {
	
	@Value("${twillio.phone.number: #{null}}")
	private String phoneNumber;
	
	@Value("${twillio.account.sid: #{null}}")
	private String TWILLIO_ACCOUNT_SID;
	
	@Value("${twillio.account.token: #{null}}")
	private String TWILLIO_ACCOUNT_TOKEN;
	
	@Bean
	public PhoneNumber phoneNumber() {
		return new PhoneNumber(phoneNumber);
	}

	@PostConstruct
	public void initializeTwillio() {
		Twilio.init(TWILLIO_ACCOUNT_SID, TWILLIO_ACCOUNT_TOKEN);	
	}
}
