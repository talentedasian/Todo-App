package com.example.forum_4_stupid.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Todos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "todos_ids")
	private Integer id;
	
	@Column(nullable = false, length = 255 )
	private String content;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false, name = "deadline")
	private java.time.LocalDateTime deadline;
	
	@Column(nullable = false, name = "createdAt")
	private java.time.LocalDateTime created;
			
	@ManyToOne
	@JoinColumn(nullable = false, name = "users_id")
	private Users user;

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

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated() {
		this.created = LocalDateTime.now();
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Todos(Integer id, String content, String title, LocalDateTime deadline, LocalDateTime created, Users user) {
		super();
		this.id = id;
		this.content = content;
		this.title = title;
		this.deadline = deadline;
		this.created = created;
		this.user = user;
	}

	public Todos() {
		super();
		this.created = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "Todos [id=" + id + ", content=" + content + ", title=" + title + ", deadline=" + deadline + ", created="
				+ created + ", user=" + user + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((deadline == null) ? 0 : deadline.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Todos other = (Todos) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (deadline == null) {
			if (other.deadline != null)
				return false;
		} else if (!deadline.equals(other.deadline))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}


}
