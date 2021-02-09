package com.example.forum_4_stupid.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comments {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer comments_id;
	
	@Column(nullable = false)
	private Instant dateCreated;
	
	@Column(nullable = false, length = 500)
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "users_id", nullable = false)
	private Users commentedBy;
	
	@ManyToOne
	@JoinColumn(name = "posts_id", nullable = false)
	private Posts posts;

	public Integer getComments_id() {
		return comments_id;
	}

	public void setComments_id(Integer comments_id) {
		this.comments_id = comments_id;
	}

	public Instant getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Instant dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Users getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(Users commentedBy) {
		this.commentedBy = commentedBy;
	}

	public Posts getPosts() {
		return posts;
	}

	public void setPosts(Posts posts) {
		this.posts = posts;
	}

	public Comments(Integer comments_id, Instant dateCreated, String content, Users commentedBy) {
		super();
		this.comments_id = comments_id;
		this.dateCreated = dateCreated;
		this.content = content;
		this.commentedBy = commentedBy;
	}

	@Override
	public String toString() {
		return "Comments [comments_id=" + comments_id + ", dateCreated=" + dateCreated + ", content=" + content
				+ ", commentedBy=" + commentedBy + ", posts=" + posts + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commentedBy == null) ? 0 : commentedBy.hashCode());
		result = prime * result + ((comments_id == null) ? 0 : comments_id.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result + ((posts == null) ? 0 : posts.hashCode());
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
		Comments other = (Comments) obj;
		if (commentedBy == null) {
			if (other.commentedBy != null)
				return false;
		} else if (!commentedBy.equals(other.commentedBy))
			return false;
		if (comments_id == null) {
			if (other.comments_id != null)
				return false;
		} else if (!comments_id.equals(other.comments_id))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (posts == null) {
			if (other.posts != null)
				return false;
		} else if (!posts.equals(other.posts))
			return false;
		return true;
	}
	
	
	
}
