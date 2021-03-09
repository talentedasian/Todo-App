package com.example.forum_4_stupid.dtoMapper.interfaces;
/*
 * T type = return type
 * X type = request type
 * Z type = entity type
 */
public interface DTOMapper <T,X,Z>{
	
	public T getById(Integer id);
	
	public void save(X request);
	
	public void delete(Z entity);
}
