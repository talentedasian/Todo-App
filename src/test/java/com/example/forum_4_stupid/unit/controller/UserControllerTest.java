package com.example.forum_4_stupid.unit.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.forum_4_stupid.controller.UserController;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.UserDtoMapper;
import com.example.forum_4_stupid.hateoas.UserDTOAssembler;

@RunWith(SpringRunner.class)
public class UserControllerTest {

	@MockBean
	private UserDtoMapper mapper;
	@MockBean
	private UserDTOAssembler assembler;

	@Test
	public void verifyUserDTOAssemblerCalled() {
		UserController userController = new UserController(mapper, assembler);
		var userDTO = new UserDTO(1, "test", 0, 0);
		
		Mockito.when(mapper.getById(1)).thenReturn(userDTO);
		userController.getUserInformationById(1);
		
		verify(assembler, times(1)).toModel(new UserDTO(1,
				"test", 0, 0));
	}
	
	@Test
	public void verifyGetByIdReturnResponseEntity() {
		UserController userController = new UserController(mapper, assembler);
		var userDTO = new UserDTO(1, "test", 0, 0);
		
		when(mapper.getByUsername("test")).thenReturn(userDTO);
		EntityModel<UserDTO> entityModel = EntityModel.of(userDTO);
		entityModel.add(linkTo(methodOn(UserController.class)
				.getUserInformationById(userDTO.getId()))
				.withRel("userById"));
		
		entityModel.add(linkTo(methodOn(UserController.class)
				.getUserInformationByUsername(userDTO.getUsername()))
				.withRel("userByUsername"));
		when(assembler.toModel(userDTO)).thenReturn(entityModel);
		ResponseEntity<EntityModel<UserDTO>> user = userController.getUserInformationByUsername("test");
		
		verify(assembler, times(1)).toModel(userDTO);
		
		assertThat(user.getBody().getContent().getId(),
				equalTo(userDTO.getId()));
		assertThat(user.getBody().getContent().getUsername(),
				equalTo(userDTO.getUsername()));
	}
	
	@Test
	public void verifyGetByUsernameReturnResponseEntity() {
		UserController userController = new UserController(mapper, assembler);
		var userDTO = new UserDTO(1, "test", 0, 0);
		
		when(mapper.getById(1)).thenReturn(userDTO);
		EntityModel<UserDTO> entityModel = EntityModel.of(userDTO);
		entityModel.add(linkTo(methodOn(UserController.class)
				.getUserInformationById(userDTO.getId()))
				.withRel("userById"));
		
		entityModel.add(linkTo(methodOn(UserController.class)
				.getUserInformationByUsername(userDTO.getUsername()))
				.withRel("userByUsername"));
		when(assembler.toModel(userDTO)).thenReturn(entityModel);
		ResponseEntity<EntityModel<UserDTO>> user = userController.getUserInformationById(1);
		
		verify(assembler, times(1)).toModel(userDTO);
		
		assertThat(user.getBody().getContent().getId(),
				equalTo(userDTO.getId()));
		assertThat(user.getBody().getContent().getUsername(),
				equalTo(userDTO.getUsername()));
	}
}
