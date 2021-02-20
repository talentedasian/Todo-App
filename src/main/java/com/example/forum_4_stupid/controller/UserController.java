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

import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.dto.EmailRequest;
import com.example.forum_4_stupid.dtoMapper.EmailDtoMapper;
import com.example.forum_4_stupid.dtoMapper.UserDtoMapper;
import com.example.forum_4_stupid.model.Email;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.service.AuthService;
import com.example.forum_4_stupid.service.EmailService;
import com.example.forum_4_stupid.service.UserService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	private final AuthService authService;
	private final EmailService emailService;
	
	@Autowired
	public UserController (UserService userService, AuthService authService, EmailService emailService) {
		this.userService = userService;
		this.authService = authService;
		this.emailService = emailService;
	}
	//implement posts endpoint first
//	@GetMapping("/user/{username}")
//	public ResponseEntity<Optional<Users>> getUserInformation (@PathVariable String username) throws NotFoundException {
//		Users user = userService.getUser(username).get();
//		
//		var userDtoMapper = new UserDtoMapper();
//		
//		userDtoMapper.returnUser(user, todos);	
//		
//		
//		
//		return new ResponseEntity<Optional<Users>>(user, HttpStatus.OK);
//	}
//	
	@PostMapping("/add-email")
	public void addEmail (@ModelAttribute EmailRequest emailRequest) {
		emailService.addEmail(emailRequest);
	}
	
	@GetMapping("/email/{owner_id}")
	public ResponseEntity<EmailDTO> getEmail(@PathVariable String owner_id) {
		Users user = userService.findUserById(Integer.parseInt(owner_id)).get();
		Email email = userService.getEmail(owner_id).get(0);
		
		var emailDtoMapper = new EmailDtoMapper();
		EmailDTO emailResponse = emailDtoMapper.returnEmail(email, user);
		
		return new ResponseEntity<EmailDTO>(emailResponse, HttpStatus.OK);
	}
	
	
}
