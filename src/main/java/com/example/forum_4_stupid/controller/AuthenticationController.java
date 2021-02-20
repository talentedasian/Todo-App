package com.example.forum_4_stupid.controller;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.LoginRequest;
import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.service.AuthService;

import io.jsonwebtoken.Jwts;

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
	public ResponseEntity<String> loginUser (@ModelAttribute LoginRequest loginRequest) {
		authService.login(loginRequest);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", "auth=" + jwtLogin());
		
		return new ResponseEntity<String>("login successful", headers, HttpStatus.OK);
	}
	
	private String jwtLogin () {
		System.out.println(Thread.currentThread());
		String jws = Jwts.builder()
				.setSubject(SecurityContextHolder.getContext().getAuthentication().getName())
				.setExpiration(new Date(System.currentTimeMillis() + 43200000))
				.compact();
		
		return jws;	
	}
	
}
