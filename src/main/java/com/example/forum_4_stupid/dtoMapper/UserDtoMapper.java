package com.example.forum_4_stupid.dtoMapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.interfaces.PersistentDTOMapper;
import com.example.forum_4_stupid.dtoMapper.interfaces.UserDTOMapper;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.service.AuthService;

public class UserDtoMapper implements UserDTOMapper, PersistentDTOMapper<RegisterRequest>{
	
	@Autowired
	private AuthService authService;
	
	public UserDtoMapper() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserDTO find(Users entity) {
		var userDTO = new UserDTO();
		userDTO.setId(entity.getId());
		userDTO.setUsername(entity.getUsername());
		
		return userDTO;
	}

	@Override
	public UserDTO findbyUsername(Users entity) {
		var userDTO = new UserDTO();
		userDTO.setId(entity.getId());
		userDTO.setUsername(entity.getUsername());
		
		return userDTO;
	}

	@Override
	public void save(RegisterRequest request) {
		
		authService.signup(request);
	}

}
