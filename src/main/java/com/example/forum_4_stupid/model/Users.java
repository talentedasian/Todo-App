package com.example.forum_4_stupid.model;

import java.time.Instant;
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
	private Instant dateCreated;
	
	@JsonIgnore
	@Column(nullable = false)
	private String password;
	
	@JsonIgnore
	@Column(nullable = false)
	private boolean enabled;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Todos> todos;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Email> email;

	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Users(Integer id, String username, Instant dateCreated, String password, boolean enabled, List<Todos> todos,
			List<Email> email) {
		super();
		this.id = id;
		this.username = username;
		this.dateCreated = dateCreated;
		this.password = password;
		this.enabled = enabled;
		this.todos = todos;
		this.email = email;
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

	public Instant getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Instant dateCreated) {
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

	public List<Email> getEmail() {
		return email;
	}

	public void setEmail(List<Email> email) {
		this.email = email;
	}
	
}
