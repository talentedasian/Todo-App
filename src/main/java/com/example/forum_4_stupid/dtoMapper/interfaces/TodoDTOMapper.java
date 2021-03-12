package com.example.forum_4_stupid.dtoMapper.interfaces;

import java.util.List;

public interface TodoDTOMapper<T, X, Z> extends DTOMapper<T, X, Z>{
	
	public List<T> getAllByUserId(Integer id);
	
	public List<T> getAllByUserUsername(String username);
	
}
