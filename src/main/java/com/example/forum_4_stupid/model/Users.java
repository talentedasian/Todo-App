package com.example.forum_4_stupid.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer users_id;
	
	@Column(nullable = false, unique = true, length = 15)
	private String username;
	
	@Column(nullable = false)
	private Instant dateCreated;
	
	@Column(nullable = false)
	private String password;
	
	@OneToMany(mappedBy = "commentedBy")
	private List<Comments> comments;
	
	@OneToMany(mappedBy = "createdBy")
	private List<Posts> posts;

	public Integer getUsers_id() {
		return users_id;
	}

	public void setUsers_id(Integer users_id) {
		this.users_id = users_id;
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

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	public List<Posts> getPosts() {
		return posts;
	}

	public void setPosts(List<Posts> posts) {
		this.posts = posts;
	}

	public Users(Integer users_id, String username, Instant dateCreated, String password) {
		super();
		this.users_id = users_id;
		this.username = username;
		this.dateCreated = dateCreated;
		this.password = password;
	}
	
	
	
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Users [users_id=" + users_id + ", username=" + username + ", dateCreated=" + dateCreated + ", password="
				+ password + ", comments=" + comments + ", posts=" + posts + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((posts == null) ? 0 : posts.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((users_id == null) ? 0 : users_id.hashCode());
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
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (posts == null) {
			if (other.posts != null)
				return false;
		} else if (!posts.equals(other.posts))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (users_id == null) {
			if (other.users_id != null)
				return false;
		} else if (!users_id.equals(other.users_id))
			return false;
		return true;
	}
	
	

	
}
