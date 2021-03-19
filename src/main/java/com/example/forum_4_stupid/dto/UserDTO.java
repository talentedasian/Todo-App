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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + totalEmails;
		result = prime * result + totalTodos;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (totalEmails != other.totalEmails)
			return false;
		if (totalTodos != other.totalTodos)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}
