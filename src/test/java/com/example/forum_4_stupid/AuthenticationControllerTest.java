package com.example.forum_4_stupid;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.forum_4_stupid.controller.AuthenticationController;
import com.example.forum_4_stupid.dtoMapper.UserDtoMapper;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.service.AuthService;
import com.example.forum_4_stupid.service.JwtProvider;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AuthService authService;
	@MockBean
	private JwtProvider jwtProvider;
	@MockBean
	private UserDtoMapper userDtoMapper;
	@MockBean
	private UsersRepository repo;
	
	@Test
	public void assertThatSignUpReturnStatusCreated() throws URISyntaxException, Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post(new URI("/auth/signup"))
				.param("username", "test")
				.param("password", "testpassword"))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void assertThatloginShouldReturnJwt() throws URISyntaxException, Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post(new URI("/auth/login"))
				.param("username", "test")
				.param("password", "testpassword"))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
