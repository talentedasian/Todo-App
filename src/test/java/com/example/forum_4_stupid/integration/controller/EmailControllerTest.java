package com.example.forum_4_stupid.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.forum_4_stupid.controller.EmailController;
import com.example.forum_4_stupid.repository.EmailRepository;
import com.example.forum_4_stupid.repository.UsersRepository;

@WebMvcTest(controllers = EmailController.class)
@AutoConfigureMockMvc(print = MockMvcPrint.DEFAULT)
public class EmailControllerTest {
	
	@MockBean
	private static EmailRepository emailRepo;
	@MockBean
	private static UsersRepository userRepo;
	
//	@Test
//	public void should 
	
}
