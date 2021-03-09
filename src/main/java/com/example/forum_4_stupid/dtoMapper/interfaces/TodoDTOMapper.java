package com.example.forum_4_stupid.dtoMapper.interfaces;

public interface TodoDTOMapper<T, X, Z> extends DTOMapper<T, X, Z>{
	
	public T getAllByUserId(Integer id);
	
	public T getAllByUserUsername(String username);
	
}
