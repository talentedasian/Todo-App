package com.example.forum_4_stupid.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.LoginRequest;
import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.UserDtoMapper;
import com.example.forum_4_stupid.service.AuthService;
import com.example.forum_4_stupid.service.JwtProvider;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private final JwtProvider jwtProvider;
	private final AuthService authService;
	private final UserDtoMapper userMapper;
	
	public AuthenticationController(JwtProvider jwtProvider, UserDtoMapper userDtoMapper
			, AuthService authService) {
		this.jwtProvider = jwtProvider;
		this.authService = authService;
		this.userMapper = userDtoMapper;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<Void> signupUser (@ModelAttribute RegisterRequest registerRequest) {
		UserDTO savedUser = userMapper.save(registerRequest);
		
		savedUser.add(linkTo(methodOn(UserController.class)
				.getUserInformationById(savedUser.getId()))
				.withSelfRel());
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
				}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginUser (@ModelAttribute LoginRequest loginRequest) throws IllegalArgumentException	 {
		authService.login(loginRequest);
		HttpHeaders headers = new HttpHeaders();
		String jwt = jwtProvider.jwtLogin(loginRequest);
		
		return new ResponseEntity<String>(jwt, headers, HttpStatus.OK);
	}
	
}
