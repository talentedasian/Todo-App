package com.example.forum_4_stupid.dto;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

public class TodoDTO extends RepresentationModel<TodoDTO>{
	
	private Integer id;
	private String content;
	private String title;
	private boolean isSendable;
	private LocalDateTime createdAt;
	private LocalDateTime deadline;
	private UserDTO user;
	private TodoSendStatusDTO todoSendStatus;
	
	public TodoSendStatusDTO getTodoSendStatus() {
		return todoSendStatus;
	}

	public void setTodoSendStatus(TodoSendStatusDTO todoSendStatus) {
		this.todoSendStatus = todoSendStatus;
	}

	public boolean isSendable() {
		return isSendable;
	}

	public void setSendable(boolean isSendable) {
		this.isSendable = isSendable;
	}

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
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public LocalDateTime getDeadline() {
		return deadline;
	}
	
	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}
	
	public TodoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TodoDTO(Integer id, String content, String title, LocalDateTime createdAt, LocalDateTime deadline, UserDTO user) {
		super();
		this.id = id;
		this.content = content;
		this.title = title;
		this.createdAt = createdAt;
		this.deadline = deadline;
		this.user = user;
	}

	@Override
	public String toString() {
		return "TodoDTO [id=" + id + ", content=" + content + ", title=" + title + ", createdAt=" + createdAt
				+ ", deadline=" + deadline + ", user=" + user + "]";
	}
	
}