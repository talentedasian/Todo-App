package com.example.forum_4_stupid.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.JwtKeys;
import com.example.forum_4_stupid.dto.LoginRequest;
import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.UsersRepository;

import io.jsonwebtoken.Jwts;
@Component
public class JwtProvider {
	
	
	private final UsersRepository usersRepository;
	
	@Autowired
	public JwtProvider(UsersRepository usersRepository) {
		super();
		this.usersRepository = usersRepository;
	}
	
	public String jwtLogin (LoginRequest loginRequest) {
		Users user = usersRepository.findByUsername(loginRequest.getUsername()).get();
		System.out.println(JwtKeys.getSigningKey());
		
		String jws = Jwts.builder()
				.signWith(JwtKeys.getSigningKey())
				.setSubject(loginRequest.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + 43200000))
				.setId(user.getId().toString())
				.compact();
		
		return jws;	
	}
	
	public String jwtLogin (RegisterRequest registerRequest) {
		Users user = usersRepository.findByUsername(registerRequest.getUsername()).get();
		
		String jws = Jwts.builder()
				.setSubject(registerRequest.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + 43200000))
				.setId(user.getId().toString())
				.compact();
		
		return jws;	
	}
	
}
