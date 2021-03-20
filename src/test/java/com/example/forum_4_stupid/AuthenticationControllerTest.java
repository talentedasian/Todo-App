package com.example.forum_4_stupid;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.forum_4_stupid.controller.AuthenticationController;
import com.example.forum_4_stupid.dto.LoginRequest;
import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.UserDtoMapper;
import com.example.forum_4_stupid.hateoas.UserDTOAssembler;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.service.AuthService;
import com.example.forum_4_stupid.service.JwtProvider;
import com.example.forum_4_stupid.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AuthenticationController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
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
	@Autowired
	private WebApplicationContext context;
	
	@Before
	public void setUpMockMvc() {
		mockMvc = MockMvcBuilders.standaloneSetup(context.getBean(AuthenticationController.class))
				.build();
	}
	
	@Before
	public void setUpRepo() {
		Users user = new Users(1, "test", Instant.now(), "testpassword", true,null, null);
		context.getBean(UsersRepository.class).save(user);
	}
	
	@Test
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
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.content().contentType(org.springframework.hateoas.MediaTypes.HAL_JSON));
		
	}
	
	@Test
	public void assertThatLoginShouldReturnJwt() throws URISyntaxException, Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "*/*");
		headers.setConnection("keep-alive");
		headers.setContentLength(50L);
		when(provider.jwtLogin(new LoginRequest("test", "testpassword"))).thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiZXhwIjoxNjE2MTY2MDk0LCJqdGkiOiIxIn0.lTELmO2V3CZn4KrpTDofqgq65WtrCVlZoFWhnLctWO8");
		this.mockMvc.perform(MockMvcRequestBuilders.post(new URI("/auth/login"))
				.content("{\n\"username\": \"test\",\n\"password\": \"testpassword\"\n}")
				.contentType(MediaType.APPLICATION_JSON)
				.headers(headers))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.header().exists("Authorization"));	
	}
	
}
