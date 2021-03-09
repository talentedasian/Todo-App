package com.example.forum_4_stupid.dtoMapper.interfaces;

/*
 * T type should be a valid DTO class from DTO package.
 * X type should be a valid register request from DTO package.
 * Z type should be a valid entity class from MODEL package.
 */
public interface PersistentDTOMapper<X,Z>{
	
	public void save(X request);
	
	public void delete(Z entity);
}
