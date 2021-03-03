package com.example.forum_4_stupid.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.forum_4_stupid.dto.LoginRequest;
import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.dtoMapper.UserDtoMapper;
import com.example.forum_4_stupid.service.AuthService;
import com.example.forum_4_stupid.service.JwtProvider;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	private final UserDtoMapper userMapper;
	private final JwtProvider jwtProvider;
	private final AuthService authService;
	
	public AuthenticationController(UserDtoMapper userMapper, JwtProvider jwtProvider
			, AuthService authService) {
		this.userMapper = userMapper;
		this.jwtProvider = jwtProvider;
		this.authService = authService;
	}
	
	@PostMapping("/signup")
	public RedirectView signupUser (@ModelAttribute RegisterRequest registerRequest) {
		RedirectView redirectUserAfterSignup = userMapper.saveUser(registerRequest);
		
		return redirectUserAfterSignup;
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginUser (@ModelAttribute LoginRequest loginRequest) {
		authService.login(loginRequest);
		
		HttpHeaders headers = new HttpHeaders();
		
		String jwt = jwtProvider.jwtLogin(loginRequest);
		
		return new ResponseEntity<String>(jwt, headers, HttpStatus.OK);
	}
	
}
