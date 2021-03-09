package com.example.forum_4_stupid.dtoMapper.interfaces;

public interface UserDTOMapper<T, X, Z> extends DTOMapper<T, X, Z>{

	public T getByUsername(String username);
}
