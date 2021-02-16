package com.example.forum_4_stupid.service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.repository.VerificationTokenRepository;

@Service
public class AuthService {
	
	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public AuthService (UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public void signup (RegisterRequest registerRequest) {
		var user = new Users();
		user.setUsername(registerRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setDateCreated(Instant.now());
		user.setEnabled(false);
		usersRepository.save(user);

	}
	
	public void login () {
		
	}
	
}
