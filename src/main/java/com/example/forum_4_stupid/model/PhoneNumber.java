package com.example.forum_4_stupid.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PhoneNumber {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "phoneNumber_id")
	private Integer id;

	@Column(nullable = false, unique = true)
	private String phoneNumber;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "userKey")
	private Users user;

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

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public PhoneNumber(Integer id, String phoneNumber, Users user) {
		super();
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.user = user;
	}

	public PhoneNumber() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "phoneNumber [id=" + id + ", phoneNumber=" + phoneNumber + ", user=" + user + "]";
	}
	
}
