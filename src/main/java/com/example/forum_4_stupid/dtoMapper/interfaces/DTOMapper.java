package com.example.forum_4_stupid.dtoMapper.interfaces;
/*
 * T type = return type
 * Z type = entity type
 */
public interface DTOMapper <T,Z>{
	
	public T returnEntity(Z entity);
					
}
