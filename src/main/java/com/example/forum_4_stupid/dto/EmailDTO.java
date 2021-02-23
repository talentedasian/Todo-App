package com.example.forum_4_stupid.dto;

public class EmailDTO {
	
	private Integer id;
	private String email;
	private UserDTO usersForeignKey;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserDTO getUsersForeignKey() {
		return usersForeignKey;
	}

	public void setUsersForeignKey(UserDTO usersForeignKey) {
		this.usersForeignKey = usersForeignKey;
	}

	public EmailDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EmailDTO(Integer id, String email, UserDTO usersForeignKey) {
		super();
		this.id = id;
		this.email = email;
		this.usersForeignKey = usersForeignKey;
	}

	@Override
	public String toString() {
		return "EmailDTO [id=" + id + ", email=" + email + ", usersForeignKey=" + usersForeignKey + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((usersForeignKey == null) ? 0 : usersForeignKey.hashCode());
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
		EmailDTO other = (EmailDTO) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (usersForeignKey == null) {
			if (other.usersForeignKey != null)
				return false;
		} else if (!usersForeignKey.equals(other.usersForeignKey))
			return false;
		return true;
	}

}
