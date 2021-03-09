package com.example.forum_4_stupid.dtoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.interfaces.DTOMapper;
import com.example.forum_4_stupid.dtoMapper.interfaces.PersistentDTOMapper;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.service.AuthService;

@Component
public class UserDtoMapper implements PersistentDTOMapper<RegisterRequest, Users>, DTOMapper<UserDTO, Users>{
	
	@Autowired
	private final AuthService authService;

	public UserDtoMapper(AuthService authService) {
		super();
		this.authService = authService;
	}

	@Override
	public void save(RegisterRequest request) {
		authService.signup(request);
	}

	@Override
	public void delete(Users entity) {
		
	}

	@Override
	public UserDTO returnEntity(Users entity) {
		var userDto = new UserDTO();
		userDto.setId(entity.getId());
		userDto.setUsername(entity.getUsername());
		
		return userDto;
	}

}
