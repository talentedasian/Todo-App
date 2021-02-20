package com.example.forum_4_stupid.dto;

import java.util.Date;

public class TodoRequest {

	private String title;
	private String content;
	private Date deadline;
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
	public TodoRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
