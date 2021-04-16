package com.example.forum_4_stupid.dto;

import javax.validation.constraints.NotNull;

public class EmailRequest {
	
	@NotNull
	private String email;
	
	@NotNull
	private String username;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public EmailRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmailRequest(String email, String username) {
		super();
		this.email = email;
		this.username = username;
	}

	
}
