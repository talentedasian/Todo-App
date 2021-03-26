package com.example.forum_4_stupid.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.LoginRequest;
import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.UserDtoMapper;
import com.example.forum_4_stupid.hateoas.UserDTOAssembler;
import com.example.forum_4_stupid.service.AuthService;
import com.example.forum_4_stupid.service.JwtProvider;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private final JwtProvider jwtProvider;
	private final AuthService authService;
	private final UserDtoMapper userMapper;
	private final UserDTOAssembler userAssembler;
	
	public AuthenticationController(JwtProvider jwtProvider, AuthService authService
			, UserDtoMapper userMapper, UserDTOAssembler userAssembler) {
		this.jwtProvider = jwtProvider;
		this.userMapper = userMapper;
		this.authService = authService;
		this.userAssembler = userAssembler;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<EntityModel<UserDTO>> signupUser(@RequestBody RegisterRequest registerRequest) {
		UserDTO userDTO = userMapper.save(registerRequest);
		EntityModel<UserDTO> assembler = userAssembler.toModel(userDTO);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(org.springframework.hateoas.MediaTypes.HAL_JSON);
		
		return new ResponseEntity<EntityModel<UserDTO>>(assembler,headers,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Void> loginUser(@RequestBody LoginRequest loginRequest) throws IllegalArgumentException	 {
		authService.login(loginRequest);
		String jwt = jwtProvider.jwtLogin(loginRequest);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",jwt);
		
		return ResponseEntity.ok().build();
	}
	
}
