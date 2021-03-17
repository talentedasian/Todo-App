package com.example.forum_4_stupid.dto;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

public class TodoDTO extends RepresentationModel<TodoDTO>{
	
	private Integer id;
	private String content;
	private String title;
	private Date createdAt;
	private Date deadline;
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
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public Date getDeadline() {
		return deadline;
	}
	
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	public TodoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TodoDTO(Integer id, String content, String title, Date createdAt, Date deadline, UserDTO user) {
		super();
		this.id = id;
		this.content = content;
		this.title = title;
		this.createdAt = createdAt;
		this.deadline = deadline;
		this.user = user;
	}
	
}