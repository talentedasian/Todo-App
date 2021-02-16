package com.example.forum_4_stupid.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public void signupUser (@RequestBody RegisterRequest registerRequest) {
		authService.signup(registerRequest);
	}
	
	@PostMapping("/login")
	public void loginUser (@RequestBody LoginRequest loginRequest) {
		authService.login(loginRequest);
	}
}
