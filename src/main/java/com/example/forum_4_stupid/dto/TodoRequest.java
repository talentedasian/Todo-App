package com.example.forum_4_stupid.dto;

import java.time.LocalDateTime;

public class TodoRequest {

	private String title;
	private String content;
	private LocalDateTime deadline;
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
	public LocalDateTime getDeadline() {
		return deadline;
	}
	public void setDeadline(LocalDateTime deadline) {
		this.deadline = LocalDateTime.of()
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
	public TodoRequest(String title, String content, LocalDateTime deadline, String username) {
		super();
		this.title = title;
		this.content = content;
		this.deadline = deadline;
		this.username = username;
	}
	
}
