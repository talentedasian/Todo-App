package com.example.forum_4_stupid.service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.forum_4_stupid.dto.EmailRequest;
import com.example.forum_4_stupid.dto.LoginRequest;
import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.model.Email;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.EmailRepository;
import com.example.forum_4_stupid.repository.UsersRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {
	
	private final UsersRepository usersRepository;
	private final EmailRepository emailRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public AuthService (UsersRepository usersRepository, PasswordEncoder passwordEncoder, EmailRepository emailRepository) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailRepository = emailRepository;
	}
	
	@Transactional
	public void signup (RegisterRequest registerRequest, EmailRequest emailRequest) {
		var user = new Users();
		user.setUsername(registerRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setDateCreated(Instant.now());
		user.setEnabled(true);
		usersRepository.save(user);
		addEmail(emailRequest, user);

	}
	
	public void login (LoginRequest loginRequest) {
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		
		String jws = Jwts.builder()
							.setSubject(loginRequest.getUsername())
							.setExpiration(new Date(System.currentTimeMillis() + 43200000))
							.signWith(key)
							.compact();
	}
	
	@Transactional
	public void addEmail (EmailRequest emailRequest, Users user) {
		var email = new Email();
		email.setEmail(emailRequest.getEmail());
		email.setUser(user);
		emailRepository.save(email);
	}
	
}
