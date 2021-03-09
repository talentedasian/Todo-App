package com.example.forum_4_stupid.dtoMapper.interfaces;
/*
 * T type = return type
 * Z type = request type
 */
public interface DTOMapper <T,Z>{
	
	public T returnEntity(Z entity);
					
}
