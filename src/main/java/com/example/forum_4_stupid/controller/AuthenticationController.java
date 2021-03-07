package com.example.forum_4_stupid.controller;

import java.sql.SQLException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.LoginRequest;
import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.dtoMapper.UserDtoMapper;
import com.example.forum_4_stupid.service.AuthService;
import com.example.forum_4_stupid.service.JwtProvider;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	private final JwtProvider jwtProvider;
	private final AuthService authService;
	
	public AuthenticationController(JwtProvider jwtProvider
			, AuthService authService) {
		this.jwtProvider = jwtProvider;
		this.authService = authService;
	}
	
	@PostMapping("/signup")
	public void signupUser (@ModelAttribute RegisterRequest registerRequest) throws SQLException {
		var userMapper = new UserDtoMapper();
		
		userMapper.save(registerRequest);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginUser (@ModelAttribute LoginRequest loginRequest) throws IllegalArgumentException	 {
		authService.login(loginRequest);
		
		HttpHeaders headers = new HttpHeaders();
		
		String jwt = jwtProvider.jwtLogin(loginRequest);
		
		return new ResponseEntity<String>(jwt, headers, HttpStatus.OK);
	}
	
}
