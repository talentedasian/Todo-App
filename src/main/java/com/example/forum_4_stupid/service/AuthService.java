package com.example.forum_4_stupid.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.forum_4_stupid.dto.LoginRequest;
import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.exceptions.AccountAlreadyExistsException;
import com.example.forum_4_stupid.exceptions.BadRequestException;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.UsersRepository;

@EnableTransactionManagement
@Service
public class AuthService {
	
	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	@Autowired
	public AuthService (UsersRepository usersRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}
	
	@Transactional
	public Users signup (RegisterRequest registerRequest) {
		try {
			if (registerRequest.getPassword().length() < 8 
					|| registerRequest.getPassword().length() > 255) {
				throw new BadRequestException("Password must be greater than 8 characters or"
						+ "less than 255 characters");
			}
			var user = new Users();
			user.setUsername(registerRequest.getUsername());
			user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
			user.setDateCreated(LocalDateTime.now());
			user.setEnabled(true);
			usersRepository.save(user);
			
			return user;
		} catch (DataIntegrityViolationException e) {
			throw new AccountAlreadyExistsException("Account already exists");
		}
	}
	
	public Authentication login (LoginRequest loginRequest) {
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), 
						loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		return auth;
	}
	
}
