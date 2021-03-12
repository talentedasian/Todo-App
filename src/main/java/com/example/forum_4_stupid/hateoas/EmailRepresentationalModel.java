package com.example.forum_4_stupid.hateoas;

import org.springframework.hateoas.RepresentationModel;

public class EmailRepresentationalModel extends RepresentationModel<EmailRepresentationalModel>{

	private String id, email;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
