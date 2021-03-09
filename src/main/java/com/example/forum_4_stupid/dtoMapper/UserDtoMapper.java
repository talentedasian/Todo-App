package com.example.forum_4_stupid.dtoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.interfaces.UserDTOMapper;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.service.AuthService;
import com.example.forum_4_stupid.service.UserService;

@Component
public class UserDtoMapper implements UserDTOMapper<UserDTO,RegisterRequest,Users>{
	
	private final AuthService authService;
	private final UserService userService;

	@Autowired
	public UserDtoMapper(AuthService authService, UserService userService) {
		super();
		this.authService = authService;
		this.userService = userService;
	}

	@Override
	public UserDTO getById(Integer id) {
		var userDTO = new UserDTO();
		Users users = getUserById(id);
		userDTO.setId(users.getId());
		userDTO.setUsername(users.getUsername());
		
		return userDTO;
	}

	@Override
	public void save(RegisterRequest request) {
		authService.signup(request);
	}

	@Override
	public void delete(Users entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserDTO getByUsername(String username) {
		var userDTO = new UserDTO();
		Users users = getUserByUsername(username);
		userDTO.setId(users.getId());
		userDTO.setUsername(users.getUsername());
		
		return userDTO;
	}
	
	private Users getUserById(Integer id) {
		Users users = userService.findUserById(id);
		
		return users;
	}
	
	private Users getUserByUsername(String username) {
		Users users = userService.findUserByUsername(username);
		
		return users;
	}

}
