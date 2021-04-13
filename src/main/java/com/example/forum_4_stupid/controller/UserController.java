package com.example.forum_4_stupid.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.UserDtoMapper;
import com.example.forum_4_stupid.hateoas.UserDTOAssembler;
import com.example.forum_4_stupid.utility.NestedDTOAssembler;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserDtoMapper userDtoMapper;
	private final UserDTOAssembler userAssembler;
	
	public UserController (UserDtoMapper userDtoMapper, UserDTOAssembler userAssembler) {
		this.userDtoMapper = userDtoMapper;
		this.userAssembler = userAssembler;
	}
	
	@GetMapping("/userById/{id}")
	public ResponseEntity<EntityModel<UserDTO>> getUserInformationById (@PathVariable Integer id) {
		var user = userDtoMapper.getById(id);
		EntityModel<UserDTO> assembler = userAssembler.toModel(user);
		
		return new ResponseEntity<EntityModel<UserDTO>>(assembler,getHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/userByUsername")
	public ResponseEntity<EntityModel<UserDTO>> getUserInformationByUsername (@RequestParam String username) {
		var user = userDtoMapper.getByUsername(username);
		EntityModel<UserDTO> assembler = userAssembler.toModel(user);
		
		return new ResponseEntity<EntityModel<UserDTO>>(assembler,getHeaders(),HttpStatus.OK);
	}
	
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(org.springframework.hateoas.MediaTypes.HAL_JSON);
		
		return headers;
	}
	
}
