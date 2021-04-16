package com.example.forum_4_stupid.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_ids")
	private Integer id;
	
	@Column(nullable = false, unique = true, length = 15)
	private String username;
	
	@Column(nullable = false)
	private LocalDateTime dateCreated;
	
	@JsonIgnore
	@Column(nullable = false)
	private String password;
	
	@JsonIgnore
	@Column(nullable = false)
	private boolean enabled;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Todos> todos;

	@OneToMany(mappedBy = "user")
	private List<PhoneNumber> phoneNumber;

	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Users(Integer id, String username, LocalDateTime dateCreated, String password, boolean enabled, List<Todos> todos,
			List<PhoneNumber> phoneNumber) {
		super();
		this.id = id;
		this.username = username;
		this.dateCreated = dateCreated;
		this.password = password;
		this.enabled = enabled;
		this.todos = todos;
		this.phoneNumber = phoneNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Todos> getTodos() {
		return todos;
	}

	public void setTodos(List<Todos> todos) {
		this.todos = todos;
	}

	public List<PhoneNumber> getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(List<PhoneNumber> phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", dateCreated=" + dateCreated + ", password=" + password
				+ ", enabled=" + enabled + ", todos=" + todos + ", phoneNumber=" + phoneNumber + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((todos == null) ? 0 : todos.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Users other = (Users) obj;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (enabled != other.enabled)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (todos == null) {
			if (other.todos != null)
				return false;
		} else if (!todos.equals(other.todos))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}
