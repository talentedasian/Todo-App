package com.example.forum_4_stupid.twillio;

import javax.annotation.PostConstruct;

public class TwillioEnvironmentVariables {

	@PostConstruct
	public void setUpTwillio() {
		System.setProperty("TWILLIO_PHONE_NUMBER", "+639153506169");
		System.setProperty("TWILLIO_AUTH_TOKEN", "+639153506169");
		System.setProperty("TWILLIO_AUTH_SID", "b49fd7c02f4dc2b7c76f62c36566cdc2");
		
	}
}
