package com.example.forum_4_stupid.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

@Component
public class PhoneNumberDTO extends RepresentationModel<PhoneNumberDTO>{
	
	private Integer id;
	private String phoneNumber;
	private UserDTO user;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public UserDTO getUser() {
		return user;
	}
	
	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	public PhoneNumberDTO(Integer id, String phoneNumber, UserDTO user) {
		super();
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.user = user;
	}

	public PhoneNumberDTO() {
		super();
	}

	@Override
	public String toString() {
		return "PhoneNumberDTO [id=" + id + ", phoneNumber=" + phoneNumber + ", user=" + user + "]";
	}

}
