package com.example.forum_4_stupid.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	@GetMapping("/signup")
	public void signupUser (@RequestBody RegisterRequest registerRequest) {
		authService.signup(registerRequest);
	}
	
	//change to redirect!!
	@PostMapping("/verify")
	public void verifyEmailUser (@RequestParam String token) {
		
	}
	
	@PostMapping("/login")
	public void loginUser () {
		
	}
}
