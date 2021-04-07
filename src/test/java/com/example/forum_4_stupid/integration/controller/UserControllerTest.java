package com.example.forum_4_stupid.integration.controller;
 
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.forum_4_stupid.controller.UserController;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.UserDtoMapper;
import com.example.forum_4_stupid.hateoas.UserDTOAssembler;
import com.example.forum_4_stupid.repository.UsersRepository;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	private MockMvc mockMvc;
	@MockBean
	private UserDtoMapper mapper;
	@MockBean
	private UserDTOAssembler assembler;
	@MockBean
	private UsersRepository repo;
	@Autowired
	private WebApplicationContext context;
	@MockBean
	private UserDetailsService service;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(context.getBean(UserController.class))
				.alwaysDo(MockMvcResultHandlers.print())
				.build();
	}
	
	@org.junit.jupiter.api.Test
	public void shouldReturnStatusOk() throws URISyntaxException, Exception {
		var userDTO = new UserDTO();
		userDTO.setId(1);
		userDTO.setUsername("test");
		userDTO.setTotalEmails(0);
		userDTO.setTotalTodos(0);
		
		when(mapper.getById(1)).thenReturn(userDTO);
		userDTO.add(linkTo(methodOn(UserController.class).getUserInformationById(userDTO.getId()))
				.withRel("userById"));
		userDTO.add(linkTo(methodOn(UserController.class).getUserInformationByUsername(userDTO.getUsername()))
				.withRel("userByUsername"));
		EntityModel<UserDTO> ass = EntityModel.of(userDTO);
		when(assembler.toModel(userDTO)).thenReturn(ass);
		mockMvc.perform(get(new URI("/api/user/userById/1"))
				.content("utf-8"))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		Mockito.verify(assembler, Mockito.times(1)).toModel(userDTO);
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("Should ReturnExpectedJsonOutput When GetMappingUserByUserId")
	public void shouldReturnExpectedOutputs() throws URISyntaxException, Exception {
		var userDTO = new UserDTO();
		userDTO.setId(1);
		userDTO.setUsername("test");
		userDTO.setTotalEmails(0);
		userDTO.setTotalTodos(0);
		
		when(mapper.getById(1)).thenReturn(userDTO);
		EntityModel<UserDTO> ass = EntityModel.of(userDTO);
		ass.add(linkTo(methodOn(UserController.class).getUserInformationById(userDTO.getId()))
				.withRel("userById"));
		ass.add(linkTo(methodOn(UserController.class).getUserInformationByUsername(userDTO.getUsername()))
				.withRel("userByUsername"));
		when(assembler.toModel(userDTO)).thenReturn(ass);
		mockMvc.perform(get(new URI("/api/user/userById/1")))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(org.springframework.hateoas.MediaTypes.HAL_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("id", equalTo(userDTO.getId())))
		.andExpect(MockMvcResultMatchers.jsonPath("username", equalTo(userDTO.getUsername())))
		.andExpect(MockMvcResultMatchers.jsonPath("totalEmails", equalTo(userDTO.getTotalEmails())))
		.andExpect(MockMvcResultMatchers.jsonPath("totalTodos", equalTo(userDTO.getTotalTodos())))
		.andExpect(MockMvcResultMatchers.jsonPath("links[0].rel", equalTo("userById")))
		.andExpect(MockMvcResultMatchers.jsonPath("links[1].rel", equalTo("userByUsername")))
		.andExpect(MockMvcResultMatchers.jsonPath("links[0].href", equalTo("/api/user/userById/1")))
		.andExpect(MockMvcResultMatchers.jsonPath("links[1].href", equalTo("/api/user/userByUsername?username=test")));
		
		Mockito.verify(assembler, Mockito.times(1)).toModel(userDTO);
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("Should ReturnExpectedJsonOutput When GetMappingUserByUserId")
	public void shouldReturnExpectedOutputs2() throws URISyntaxException, Exception {
		var userDTO = new UserDTO();
		userDTO.setId(1);
		userDTO.setUsername("test");
		userDTO.setTotalEmails(0);
		userDTO.setTotalTodos(0);

		when(mapper.getByUsername("test")).thenReturn(userDTO);
		EntityModel<UserDTO> ass = EntityModel.of(userDTO);
		ass.add(linkTo(methodOn(UserController.class).getUserInformationById(userDTO.getId()))
				.withRel("userById"));
		ass.add(linkTo(methodOn(UserController.class).getUserInformationByUsername(userDTO.getUsername()))
				.withRel("userByUsername"));
		when(assembler.toModel(userDTO)).thenReturn(ass);
		mockMvc.perform(get(new URI("/api/user/userByUsername?username=test")))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(org.springframework.hateoas.MediaTypes.HAL_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("id", equalTo(userDTO.getId())))
		.andExpect(MockMvcResultMatchers.jsonPath("username", equalTo(userDTO.getUsername())))
		.andExpect(MockMvcResultMatchers.jsonPath("totalEmails", equalTo(userDTO.getTotalEmails())))
		.andExpect(MockMvcResultMatchers.jsonPath("totalTodos", equalTo(userDTO.getTotalTodos())))
		.andExpect(MockMvcResultMatchers.jsonPath("links[0].rel", equalTo("userById")))
		.andExpect(MockMvcResultMatchers.jsonPath("links[1].rel", equalTo("userByUsername")))
		.andExpect(MockMvcResultMatchers.jsonPath("links[0].href", equalTo("/api/user/userById/1")))
		.andExpect(MockMvcResultMatchers.jsonPath("links[1].href", equalTo("/api/user/userByUsername?username=test")));
		
		Mockito.verify(assembler, Mockito.times(1)).toModel(userDTO);
	}
	
}
