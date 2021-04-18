package com.example.forum_4_stupid.twillio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TwillioSid {
	
	@Value("${twillio.account.sid}")
	private String TWILLIO_ACCOUNT_SID;

}
