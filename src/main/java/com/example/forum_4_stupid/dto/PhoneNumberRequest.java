package com.example.forum_4_stupid.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PhoneNumberRequest {
	
	@NotNull
	@Size(min = 11, max = 20)
	private String phoneNumber;
	
	@NotNull
	private String username;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public PhoneNumberRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PhoneNumberRequest(@NotNull String phoneNumber, @NotNull String username) {
		super();
		this.phoneNumber = phoneNumber;
		this.username = username;
	}

	@Override
	public String toString() {
		return "PhoneNumberRequest [phoneNumber=" + phoneNumber + ", username=" + username + "]";
	}
	
}
