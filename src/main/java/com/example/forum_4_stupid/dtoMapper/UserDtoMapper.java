package com.example.forum_4_stupid.dtoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.RedirectView;

import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.service.AuthService;

@Component
public class UserDtoMapper {
	
	private final AuthService authService;
	
	@Autowired
	public UserDtoMapper(AuthService authService) {
		this.authService = authService;
	}
	
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
	
	public RedirectView saveUser (RegisterRequest registerRequest) {
		RedirectView todosRedirect = new RedirectView("http://localhost:8080/user/userByUsername?username=" + registerRequest.getUsername());
		authService.signup(registerRequest);
		
		return todosRedirect;
	}
	
}
