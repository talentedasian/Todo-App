package com.example.forum_4_stupid.dtoMapper.interfaces;

import java.util.List;

public interface EmailDTOMapper<T, X, Z> extends DTOMapper<T, X, Z>{

	public List<T> getAllEmailByUsersUsername(String username);
	
	public List<T> getAllEmailByUsersId(Integer id);
}
