package com.example.forum_4_stupid.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Email {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "email_id")
	private Integer id;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Users.class)
	@JoinColumn(nullable = true, name = "userKey", referencedColumnName = "user_ids")
	private Users user;
	
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

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Email() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Email(Integer id, String email, Users user) {
		super();
		this.id = id;
		this.email = email;
		this.user = user;
	}
	
}
