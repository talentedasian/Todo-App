package com.example.forum_4_stupid.integration.controller;

import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;

import org.hamcrest.CoreMatchers;
import org.mockito.Mockito;
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
import org.springframework.web.context.WebApplicationContext;

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
	
	@org.junit.jupiter.api.Test
	public void assertThatSignUpReturnStatusCreated() throws URISyntaxException, Exception {
		var registerRequest = new RegisterRequest("test", "testpassword");
		var userDTO = new UserDTO();
		userDTO.setId(1);
		userDTO.setUsername("test");
		userDTO.setTotalEmails(0);
		userDTO.setTotalTodos(0);
		when(mapper.save(registerRequest)).thenReturn(userDTO);
		EntityModel<UserDTO> ass = Mockito.spy(UserDTOAssembler.class).toModel(userDTO);
		when(assembler.toModel(userDTO)).thenReturn(ass);
		this.mockMvc.perform(MockMvcRequestBuilders.post(new URI("/auth/signup"))
				.characterEncoding("utf-8")
				.content("{\n\"username\": \"test\",\n\"password\": \"testpassword\"\n}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.content().contentType(org.springframework.hateoas.MediaTypes.HAL_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("id", CoreMatchers.is(userDTO.getId())))
		.andExpect(MockMvcResultMatchers.jsonPath("username", CoreMatchers.is(userDTO.getUsername())))
		.andExpect(MockMvcResultMatchers.jsonPath("totalEmails", CoreMatchers.is(userDTO.getTotalEmails())))
		.andExpect(MockMvcResultMatchers.jsonPath("totalTodos", CoreMatchers.is(userDTO.getTotalTodos())));
		
	}
	
	@org.junit.jupiter.api.Test
	public void assertThatLoginShouldReturnJwt() throws URISyntaxException, Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post(new URI("/auth/login"))
				.content("{\n\"username\": \"test1\",\n\"password\": \"testpassword\"\n}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.cookie().httpOnly("jwt", true));	
	}
	
	@org.junit.jupiter.api.Test
	public void shouldBeBadRequest() throws URISyntaxException, Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post(new URI("/auth/signup"))
				.characterEncoding("utf-8")
				.content("{\n\"username\": \"test\",\n\"password\": \"test\"\n}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
}
