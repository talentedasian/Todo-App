package com.example.forum_4_stupid;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.forum_4_stupid.controller.UserController;
import com.example.forum_4_stupid.service.UserService;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	@MockBean
	private UserController userController;
	
	@Test
	public void assertThatSignupControllerIsSaved() throws URISyntaxException, Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get(new URI("/api/user/userById/1")))
		.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(403));
	}
	
	
}
