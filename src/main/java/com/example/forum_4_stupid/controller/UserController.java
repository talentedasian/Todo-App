package com.example.forum_4_stupid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.UserDtoMapper;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	private final UserDtoMapper userDtoMapper;
	
	@Autowired
	public UserController (UserService userService, UserDtoMapper userDtoMapper) {
		this.userService = userService;
		this.userDtoMapper = userDtoMapper;
	}
	
	@GetMapping("/userById/{id}")
	public ResponseEntity<UserDTO> getUserInformationById (@PathVariable String id) {
		Users users = userService.findUserById(Integer.parseInt(id)).get();
		
		var user = userDtoMapper.returnUser(users);
		
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}
	
	@GetMapping("/userByUsername")
	public ResponseEntity<Users> getUserInformationByUsername (@RequestParam String username) {
		Users users = userService.getUser(username).get();
		
		var user = userDtoMapper.returnUser(users);
		user.setUsername("tanga");
		
		return new ResponseEntity<Users>(users, HttpStatus.OK);
	}
	
}
