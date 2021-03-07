package com.example.forum_4_stupid.dto;

import java.util.Date;

public class TodoRequest {

	private String title;
	private String content;
	private Date deadline;
	private String username;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public TodoRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TodoRequest(String title, String content, Date deadline, String username) {
		super();
		this.title = title;
		this.content = content;
		this.deadline = deadline;
		this.username = username;
	}
	
}
