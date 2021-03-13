package com.example.forum_4_stupid.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

@Component
public class EmailDTO extends RepresentationModel<EmailDTO>{
	
	private Integer id;
	private String email;
	private UserDTO user;
	
	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EmailDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EmailDTO(Integer id, String email) {
		super();
		this.id = id;
		this.email = email;
	}

}
