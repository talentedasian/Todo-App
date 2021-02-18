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
	
	@Column(nullable = false)
	private String email;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Users.class)
	@JoinColumn(nullable = true, name = "userKey", referencedColumnName = "user_ids")
	private Users user;
	
	@Column(name = "userKey", insertable = false, updatable = false)
	private Integer userKey;

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
	
	

	public Integer getuserKey() {
		return userKey;
	}

	public void setuserKey(Integer userKey) {
		this.userKey = userKey;
	}

	public Email() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Email(Integer id, String email, Users user, Integer userKey) {
		super();
		this.id = id;
		this.email = email;
		this.user = user;
		this.userKey = userKey;
	}

	@Override
	public String toString() {
		return "Email [id=" + id + ", email=" + email + ", user=" + user + ", userKey=" + userKey + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((userKey == null) ? 0 : userKey.hashCode());
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
		Email other = (Email) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (userKey == null) {
			if (other.userKey != null)
				return false;
		} else if (!userKey.equals(other.userKey))
			return false;
		return true;
	}

}
