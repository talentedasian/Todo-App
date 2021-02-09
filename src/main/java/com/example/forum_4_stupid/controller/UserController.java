package com.example.forum_4_stupid.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.UsersRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UsersRepository usersRepository;
	
	@Autowired
	public UserController (UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}
	
	@GetMapping("/info")
	public Optional<Users> getUserInformation (@RequestParam Integer id) {
		Optional<Users> user = usersRepository.findById(id);
		
		return user;
	}
	
}
