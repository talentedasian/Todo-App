package com.example.forum_4_stupid;

import java.net.URI;
import java.net.URISyntaxException;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
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

import com.example.forum_4_stupid.controller.UserController;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
public class ProtectedResourceTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	@MockBean
	private UserController userController;
	@MockBean
	private UsersRepository repo;
	
	private String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnYWdvIiwiZXhwIjoxNjE1MTYyNjIxLCJqdGkiOiIxIn0.vmJSDU9S4Kznv6tGYRjcJdcGif8-y01jv_ktC0Y2cpk";
	private String expectedJwtResponseWhenExpired = "{\"reason\":\"Server might have restarted or JWT was tampered\",\"code\":\"401\",\"err\":\"Invalid Signature of JWT\"}";
	private String expectedJwtResponseNotFound = "{\"code\":\"401\",\"err\":\"No JWT Found on Authorization Header\"}";
	
	@Test
	public void assertThatAccessingProtectedResourceReturnForbidden() throws URISyntaxException, Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get(new URI("/api/user/userById/1")))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().is(401));
	}
	
	@Test
	public void assertThatWExpiredJwt() throws URISyntaxException, Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get(new URI("/api/user/userById/1"))
				.header("Authorization", jwt))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().is(401))
		.andExpect(MockMvcResultMatchers.content().json(expectedJwtResponseWhenExpired));
		
	}
	
	@Test
	public void assertThatJwtReturnNoJwt() throws URISyntaxException, Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get(new URI("/api/user/userById/1")))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.status().is(401))
		.andExpect(MockMvcResultMatchers.content().json(expectedJwtResponseNotFound));
	}
	
}
