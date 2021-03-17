package com.example.forum_4_stupid.dto;

import org.springframework.hateoas.RepresentationModel;

public class UserDTO extends RepresentationModel<UserDTO>{
	
	private Integer id;
	private String username;
	private int totalEmails, totalTodos;
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
	public int getTotalEmails() {
		return totalEmails;
	}
	public void setTotalEmails(int totalEmails) {
		this.totalEmails = totalEmails;
	}
	public int getTotalTodos() {
		return totalTodos;
	}
	public void setTotalTodos(int totalTodos) {
		this.totalTodos = totalTodos;
	}
	
	public UserDTO(Integer id, String username, int totalEmails, int totalTodos) {
		super();
		this.id = id;
		this.username = username;
		this.totalEmails = totalEmails;
		this.totalTodos = totalTodos;
	}
	
	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
