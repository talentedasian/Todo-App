package com.example.forum_4_stupid.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.EmailRequest;
import com.example.forum_4_stupid.model.Email;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.EmailRepository;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.service.AuthService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UsersRepository usersRepository;
	private final AuthService authService;
	private final EmailRepository emailRepository;
	
	@Autowired
	public UserController (UsersRepository usersRepository, AuthService authService, EmailRepository emailRepository) {
		this.usersRepository = usersRepository;
		this.authService = authService;
		this.emailRepository = emailRepository;
	}
	
	@GetMapping("/info")
	public ResponseEntity<Optional<Users>> getUserInformation (@RequestParam int id) throws NotFoundException {
		Optional<Users> user = Optional.ofNullable(usersRepository.findById(id)
												.orElseThrow(() -> new NotFoundException("account not found")));
		
		return new ResponseEntity<Optional<Users>>(user, HttpStatus.OK);
	}
	
	@GetMapping("email")
	public Optional<Email> getEmail (@RequestParam Integer id) {
		return emailRepository.findById(id);
	}
	
	
}
