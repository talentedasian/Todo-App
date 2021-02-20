package com.example.forum_4_stupid.service;

import java.security.Key;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
				(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		
	}
	
	public String jwtLogin () {
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		String jws = Jwts.builder()
				.setSubject(SecurityContextHolder.getContext().getAuthentication().getName())
				.setExpiration(new Date(System.currentTimeMillis() + 43200000))
				.signWith(key)
				.compact();
		
		return jws;		
	}
	
}
