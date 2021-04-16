package com.example.forum_4_stupid.dtoMapper.interfaces;

import java.util.List;

public interface PhoneNumberDTOMapper<T, X, Z> extends DTOMapper<T, X, Z>{

	public List<T> getAllPhoneNumbersByUsersUsername(String username);
	
	public List<T> getAllPhoneNumbersByUsersId(Integer id);
	
	
}
