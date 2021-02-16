package com.example.forum_4_stupid.dto;

import org.springframework.stereotype.Component;

@Component
public class EmailRequest {
	
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EmailRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmailRequest(String email) {
		super();
		this.email = email;
	}

	@Override
	public String toString() {
		return "EmailRequest [email=" + email + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmailRequest other = (EmailRequest) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	
}
