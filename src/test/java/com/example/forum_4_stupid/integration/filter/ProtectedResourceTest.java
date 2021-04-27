package com.example.forum_4_stupid.integration.filter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URISyntaxException;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.forum_4_stupid.controller.UserController;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.service.UserService;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(print = MockMvcPrint.DEFAULT)
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
	
	//BUG. NOT RETURNING ANY BODY
	
	@org.junit.jupiter.api.Test
	@DisplayName("Should_ReturnNoJWTFoundOnAuthHeader_When_AccessingProtectedResource_UserById")
	public void shouldReturnForbiddenAndNullAuthHeader() throws URISyntaxException, Exception {
		this.mockMvc.perform(get(new URI("/api/user/userById/1")))
		.andExpect(status().is(401))
		.andExpect(jsonPath("err", equalTo("401")))
		.andExpect(jsonPath("reason", equalTo("No JWT Found on Authorization Header")));
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("Should_ReturnStampedJwt_When_AccessingProtectedResource_UserById_With_StampedJWT")
	public void shouldReturnStampedSignatureJwt() throws URISyntaxException, Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get(new URI("/api/user/userById/1"))
				.header("Authorization", jwt))
		.andExpect(status().is(401))
		.andExpect(jsonPath("err", equalTo("401")))
		.andExpect(jsonPath("reason", equalTo("Invalid Signature of JWT")))
		.andExpect(jsonPath("additional_information", CoreMatchers.notNullValue()));
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("Should_ReturnStampedJwt_When_AccessingProtectedResource_UserByUsername_With_StampedJWT")
	public void shouldReturnStampedSignatureJwt2() throws URISyntaxException, Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get(new URI("/api/user/userByUsername?username=test"))
				.header("Authorization", jwt))
		.andExpect(status().is(401))
		.andExpect(jsonPath("err", equalTo("401")))
		.andExpect(jsonPath("reason", equalTo("Invalid Signature of JWT")))
		.andExpect(jsonPath("additional_information", CoreMatchers.notNullValue()));		
	}
	
	@org.junit.jupiter.api.Test
	@DisplayName("Should_ReturnForbiddenAndNullAuthHeader_When_AccessingProtectedResourceWithNoJWT_UserByUsername")
	public void shouldReturnForbiddenAndNullAuthHeader2() throws URISyntaxException, Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get(new URI("/api/user/userByUsername?username=test")))
		.andExpect(status().is(401))
		.andExpect(jsonPath("err", equalTo("401")))
		.andExpect(jsonPath("reason", equalTo("No JWT Found on Authorization Header")));
	}
	
}
