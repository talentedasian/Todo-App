package com.example.forum_4_stupid.service;

import java.security.KeyPair;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.forum_4_stupid.dto.LoginRequest;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.UsersRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {
	
	@Autowired
	private UsersRepository usersRepository;
	
	public static final KeyPair key () {
		return Keys.keyPairFor(SignatureAlgorithm.HS256);
	}
	
	public String jwtLogin (LoginRequest loginRequest) {
		Users user = usersRepository.findByUsername(loginRequest.getUsername()).get();
		
		String jws = Jwts.builder()
				.signWith(key().getPublic())
				.setSubject(loginRequest.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + 43200000))
				.setId(user.getId().toString())
				.compact();
		
		return jws;	
	}
	
}
