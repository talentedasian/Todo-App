package com.example.forum_4_stupid.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.EmailRequest;
import com.example.forum_4_stupid.model.Email;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.service.AuthService;
import com.example.forum_4_stupid.service.UserService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	private final AuthService authService;
	
	@Autowired
	public UserController (UserService userService, AuthService authService) {
		this.userService = userService;
		this.authService = authService;
	}
	
	@GetMapping("/user/{username}")
	public ResponseEntity<Optional<Users>> getUserInformation (@PathVariable String username) throws NotFoundException {
		Optional<Users> user = Optional.ofNullable(userService.getUser(username).orElseThrow());
		
		return new ResponseEntity<Optional<Users>>(user, HttpStatus.OK);
	}
	
	@PostMapping("/add-email")
	public void addEmail (@ModelAttribute EmailRequest emailRequest) {
		authService.addEmail(emailRequest);
	}
	
	@GetMapping("/email/{owner_id}")
	public ResponseEntity<List<Email>> getEmail(@PathVariable String owner_id) {
		return new ResponseEntity<List<Email>>(userService.getEmail(owner_id), HttpStatus.OK);
	}
	
	
}
