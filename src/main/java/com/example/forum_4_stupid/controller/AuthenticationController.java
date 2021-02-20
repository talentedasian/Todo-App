package com.example.forum_4_stupid.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.LoginRequest;
import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	private final AuthService authService;
	
	public AuthenticationController(AuthService authService) {
		this.authService = authService;
	}
	
	//change to redirect!!
	@PostMapping("/signup")
	public ResponseEntity<String> signupUser (@ModelAttribute RegisterRequest registerRequest) {
		authService.signup(registerRequest);
		String nice = "nice";
		return new ResponseEntity<>(nice,HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public void loginUser (@ModelAttribute LoginRequest loginRequest) {
		authService.login(loginRequest);
	}
}
