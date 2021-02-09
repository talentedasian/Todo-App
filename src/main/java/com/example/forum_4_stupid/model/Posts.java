package com.example.forum_4_stupid.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Posts {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer posts_id;
	
	@Column(nullable = false, length = 1000)
	private String content;
	
	@Column(nullable = false)
	private Instant dateCreated;
	
	@Column(nullable = true)
	private Instant dateEdited;
	
	@ManyToOne
	@JoinColumn(name = "users_id", referencedColumnName = "users_id")
	private Users createdBy;
	
	@OneToMany(mappedBy = "posts")
	private List<Comments> comments;

	public Integer getPosts_id() {
		return posts_id;
	}

	public void setPosts_id(Integer posts_id) {
		this.posts_id = posts_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Instant getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Instant dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Instant getDateEdited() {
		return dateEdited;
	}

	public void setDateEdited(Instant dateEdited) {
		this.dateEdited = dateEdited;
	}

	public Users getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Users createdBy) {
		this.createdBy = createdBy;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	public Posts(Integer posts_id, String content, Instant dateCreated, Instant dateEdited, Users createdBy) {
		super();
		this.posts_id = posts_id;
		this.content = content;
		this.dateCreated = dateCreated;
		this.dateEdited = dateEdited;
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "Posts [posts_id=" + posts_id + ", content=" + content + ", dateCreated=" + dateCreated + ", dateEdited="
				+ dateEdited + ", createdBy=" + createdBy + ", comments=" + comments + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result + ((dateEdited == null) ? 0 : dateEdited.hashCode());
		result = prime * result + posts_id;
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
		Posts other = (Posts) obj;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (dateEdited == null) {
			if (other.dateEdited != null)
				return false;
		} else if (!dateEdited.equals(other.dateEdited))
			return false;
		if (posts_id != other.posts_id)
			return false;
		return true;
	}
	
}
