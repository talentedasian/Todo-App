package com.example.forum_4_stupid.integration.controller;
 
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import com.example.forum_4_stupid.controller.UserController;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.UserDtoMapper;
import com.example.forum_4_stupid.hateoas.UserDTOAssembler;
import com.example.forum_4_stupid.repository.UsersRepository;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(print = MockMvcPrint.DEFAULT, addFilters = false)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserDtoMapper mapper;
	@MockBean
	private UserDTOAssembler assembler;
	@MockBean
	private UsersRepository repo;
	@MockBean
	private UserDetailsService service;
	
	private static EntityModel<UserDTO> entityModel;
	private static UserDTO userDTO = new UserDTO();
	
	@BeforeEach
	public void setUp() {
		userDTO.setId(1);
		userDTO.setUsername("test");
		userDTO.setTotalEmails(0);
		userDTO.setTotalTodos(0);
		
		entityModel = EntityModel.of(userDTO);

		entityModel.add(linkTo(methodOn(UserController.class).getUserInformationById(userDTO.getId()))
				.withRel("userById"));
		entityModel.add(linkTo(methodOn(UserController.class).getUserInformationByUsername(userDTO.getUsername()))
				.withRel("userByUsername"));
	}
	
	@org.junit.jupiter.api.Test
	public void shouldReturnStatusOk() throws URISyntaxException, Exception {
		when(mapper.getById(1)).thenReturn(userDTO);
		
		when(assembler.toModel(userDTO)).thenReturn(entityModel);
		mockMvc.perform(get(new URI("/api/user/userById/1"))
				.content("utf-8"))
		.andExpect(status().isOk());
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("Should ReturnExpectedJsonOutput When GetMappingUserByUserId")
	public void shouldReturnExpectedOutputs() throws URISyntaxException, Exception {
		when(mapper.getById(1)).thenReturn(userDTO);
		
		when(assembler.toModel(userDTO)).thenReturn(entityModel);
		
		mockMvc.perform(get(new URI("/api/user/userById/1")))
		.andExpect(status().isOk())
		.andExpect(content().contentType(org.springframework.hateoas.MediaTypes.HAL_JSON))
		.andExpect(jsonPath("id", equalTo(userDTO.getId())))
		.andExpect(jsonPath("username", equalTo(userDTO.getUsername())))
		.andExpect(jsonPath("totalEmails", equalTo(userDTO.getTotalEmails())))
		.andExpect(jsonPath("totalTodos", equalTo(userDTO.getTotalTodos())))
		.andExpect(jsonPath("_links.userById.href", equalTo("/api/user/userById/1")))
		.andExpect(jsonPath("_links.userByUsername.href", equalTo("/api/user/userByUsername?username=test")))
		.andExpect(jsonPath("_links.userById").hasJsonPath())
		.andExpect(jsonPath("_links.userByUsername").hasJsonPath());
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("Should ReturnExpectedJsonOutput When GetMappingUserByUsername")
	public void shouldReturnExpectedOutputs2() throws URISyntaxException, Exception {
		when(mapper.getByUsername("test")).thenReturn(userDTO);
		
		when(assembler.toModel(userDTO)).thenReturn(entityModel);
		
		mockMvc.perform(get(new URI("/api/user/userByUsername?username=test")))
		.andExpect(status().isOk())
		.andExpect(jsonPath("id", equalTo(userDTO.getId())))
		.andExpect(jsonPath("username", equalTo(userDTO.getUsername())))
		.andExpect(jsonPath("totalEmails", equalTo(userDTO.getTotalEmails())))
		.andExpect(jsonPath("totalTodos", equalTo(userDTO.getTotalTodos())))
		.andExpect(jsonPath("_links.userById.href", equalTo("/api/user/userById/1")))
		.andExpect(jsonPath("_links.userByUsername.href", equalTo("/api/user/userByUsername?username=test")))
		.andExpect(jsonPath("_links.userById").hasJsonPath())
		.andExpect(jsonPath("_links.userByUsername").hasJsonPath());
	}
	
	@org.junit.jupiter.api.Test
	public void shouldReturnHal_JsonContentType() throws URISyntaxException, Exception {
		when(mapper.getById(1)).thenReturn(userDTO);
		
		when(assembler.toModel(userDTO)).thenReturn(entityModel);
		
		mockMvc.perform(get(new URI("/api/user/userById/1"))
				.content("utf-8"))
		.andExpect(content().contentType(MediaTypes.HAL_JSON));
	}
	
}
