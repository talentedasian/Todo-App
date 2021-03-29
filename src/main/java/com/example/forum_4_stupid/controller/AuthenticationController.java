package com.example.forum_4_stupid.controller;

import javax.validation.Valid;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.LoginRequest;
import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.UserDtoMapper;
import com.example.forum_4_stupid.exceptions.AccountDoesNotExistException;
import com.example.forum_4_stupid.exceptions.LoginFailedException;
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
	public ResponseEntity<EntityModel<UserDTO>> signupUser(@Valid @RequestBody RegisterRequest registerRequest) {
		UserDTO userDTO = userMapper.save(registerRequest);
		EntityModel<UserDTO> assembler = userAssembler.toModel(userDTO);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(org.springframework.hateoas.MediaTypes.HAL_JSON);
		
		return new ResponseEntity<EntityModel<UserDTO>>(assembler,headers,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Void> loginUser(@RequestBody LoginRequest loginRequest) throws IllegalArgumentException {
		try {
			authService.login(loginRequest);
			String jwt = jwtProvider.jwtLogin(loginRequest);
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Set-Cookie","jwt=" + jwt + "; HttpOnly");
			
			return ResponseEntity.ok().headers(headers).build();			
		} catch (InternalAuthenticationServiceException e) {
			throw new LoginFailedException(new AccountDoesNotExistException("Account used to login does not exist"));
		} catch (BadCredentialsException e) {
			throw new LoginFailedException("Password used for login is incorrect");
		}
		
	}
	
}
