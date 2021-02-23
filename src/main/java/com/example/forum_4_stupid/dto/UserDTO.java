package com.example.forum_4_stupid.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDTO {
	
	private Integer id;
	private String username;
	@JsonIgnore
	private TodoDTO todoForeignKey;
	
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

	public TodoDTO getTodoForeignKey() {
		return todoForeignKey;
	}

	public void setTodoForeignKey(TodoDTO todoForeignKey) {
		this.todoForeignKey = todoForeignKey;
	}

	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDTO(Integer id, String username, TodoDTO todoForeignKey) {
		super();
		this.id = id;
		this.username = username;
		this.todoForeignKey = todoForeignKey;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", username=" + username + ", todoForeignKey=" + todoForeignKey + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((todoForeignKey == null) ? 0 : todoForeignKey.hashCode());
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
		UserDTO other = (UserDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (todoForeignKey == null) {
			if (other.todoForeignKey != null)
				return false;
		} else if (!todoForeignKey.equals(other.todoForeignKey))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}
