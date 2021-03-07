package com.example.forum_4_stupid.model;

import java.util.Date;

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
	
	@Column(nullable = false, name = "dateDeadline")
	private Date deadline;
	
	@Column(nullable = false, name = "dateCreated")
	private Date created;
			
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

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Todos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Todos(Integer id, String content, String title, Date deadline, Date created, Users user) {
		super();
		this.id = id;
		this.content = content;
		this.title = title;
		this.deadline = deadline;
		this.created = created;
		this.user = user;
	}

}
