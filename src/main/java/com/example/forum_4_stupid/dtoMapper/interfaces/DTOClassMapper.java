package com.example.forum_4_stupid.dtoMapper.interfaces;

public interface DTOClassMapper<T,X>{

	public T mapEntityToDTO(X entity);
}
