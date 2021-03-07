package com.example.forum_4_stupid.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.forum_4_stupid.dto.LoginRequest;
import com.example.forum_4_stupid.dto.RegisterRequest;
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
	public void signup (RegisterRequest registerRequest) {
		var user = new Users();
		user.setUsername(registerRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setDateCreated(Instant.now());
		user.setEnabled(true);
		usersRepository.save(user);
	}
	
	public void login (LoginRequest loginRequest) {
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), 
						loginRequest.getPassword(), getAuthoritiesAuthority("USER")));
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	private Collection<? extends GrantedAuthority> getAuthoritiesAuthority (String role) {
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(new SimpleGrantedAuthority(role));
		
		return list;
	}
	
}
