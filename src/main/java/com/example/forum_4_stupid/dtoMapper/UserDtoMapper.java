package com.example.forum_4_stupid.dtoMapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.service.AuthService;

public class UserDtoMapper {
	
	
	@Autowired
	private AuthService authService;
	
	public UserDTO returnUser (Users users) {
		var userDTO = new UserDTO();
		userDTO.setId(users.getId());
		userDTO.setUsername(users.getUsername());
		
		return userDTO;
	}
	
	public UserDTO returnUserForeignKey (Users users) {
		var userDTO = new UserDTO();
		userDTO.setId(users.getId());
		
		return userDTO;
	}
	
	public void saveUser (RegisterRequest registerRequest) {
		authService.signup(registerRequest);
	}
}
