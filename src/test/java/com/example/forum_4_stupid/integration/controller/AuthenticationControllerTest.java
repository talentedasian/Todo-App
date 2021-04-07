package com.example.forum_4_stupid.integration.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.forum_4_stupid.controller.AuthenticationController;
import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.UserDtoMapper;
import com.example.forum_4_stupid.hateoas.UserDTOAssembler;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.service.AuthService;
import com.example.forum_4_stupid.service.JwtProvider;
import com.example.forum_4_stupid.service.UserService;

@WebMvcTest(controllers = AuthenticationController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@AutoConfigureMockMvc(print = MockMvcPrint.DEFAULT)
public class AuthenticationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UsersRepository repo;
	@MockBean
	private JwtProvider provider;
	@MockBean
	private UserDTOAssembler assembler;
	@MockBean
	private UserDtoMapper mapper;
	@MockBean
	private AuthService auth;
	@MockBean
	private UserService userService;
	
	private static UserDTO userDTO;
	private static RegisterRequest registerRequest;
	
	@BeforeEach
	public void setUp() {
		userDTO = new UserDTO();
		userDTO.setId(1);
		userDTO.setUsername("longusername");
		userDTO.setTotalEmails(0);
		userDTO.setTotalTodos(0);
		
		registerRequest = new RegisterRequest("longusername", "testpassword");
	}
	
	@org.junit.jupiter.api.Test
	public void assertThatSignUpReturnStatusCreated() throws URISyntaxException, Exception {
		when(mapper.save(registerRequest)).thenReturn(userDTO);
		
		EntityModel<UserDTO> ass = EntityModel.of(userDTO);
		
		when(assembler.toModel(userDTO)).thenReturn(ass);
		
		mockMvc.perform(MockMvcRequestBuilders.post(new URI("/auth/signup"))
				.characterEncoding("utf-8")
				.content("{\n\"username\": \"longusername\",\n\"password\": \"testpassword\"\n}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.content().contentType(org.springframework.hateoas.MediaTypes.HAL_JSON));
		
	}
	
	@Test
	public void assertThatLoginShouldReturnJwt() throws URISyntaxException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(new URI("/auth/login"))
				.content("{\n\"username\": \"test1\",\n\"password\": \"testpassword\"\n}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.cookie().httpOnly("jwt", true));	
	}
	
	@Test
	public void shouldBeBadRequest() throws URISyntaxException, Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(new URI("/auth/signup"))
				.characterEncoding("utf-8")
				.content("{\n\"username\": \"test\",\n\"password\": \"test\"\n}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void shouldReturnExpectedDtoOutput() throws URISyntaxException, Exception {
		when(mapper.save(registerRequest)).thenReturn(userDTO);
		
		EntityModel<UserDTO> ass = EntityModel.of(userDTO);
		
		when(assembler.toModel(userDTO)).thenReturn(ass);
		
		mockMvc.perform(MockMvcRequestBuilders.post(new URI("/auth/signup"))
				.characterEncoding("utf-8")
				.content("{\n\"username\": \"longusername\",\n\"password\": \"testpassword\"\n}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("id", equalTo(userDTO.getId())))
		.andExpect(MockMvcResultMatchers.jsonPath("username", equalTo(userDTO.getUsername())))
		.andExpect(MockMvcResultMatchers.jsonPath("totalEmails", equalTo(userDTO.getTotalEmails())))
		.andExpect(MockMvcResultMatchers.jsonPath("totalTodos", equalTo(userDTO.getTotalTodos())));
	}
	
}
