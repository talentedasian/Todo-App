package com.example.forum_4_stupid.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;

import com.example.forum_4_stupid.controller.EmailController;
import com.example.forum_4_stupid.dtoMapper.EmailDtoMapper;
import com.example.forum_4_stupid.hateoas.EmailDTOAssembler;
import com.example.forum_4_stupid.repository.EmailRepository;
import com.example.forum_4_stupid.repository.UsersRepository;

@WebMvcTest(controllers = EmailController.class)
@AutoConfigureMockMvc(print = MockMvcPrint.DEFAULT)
public class EmailControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private static EmailRepository emailRepo;
	@MockBean
	private static UsersRepository userRepo;
	@MockBean
	private static EmailDTOAssembler assembler;
	@MockBean
	private static EmailDtoMapper mapper;
	
	@Test
	public void shouldReturnHal_JsonContentType() throws URISyntaxException, Exception { 
		mvc.perform(get(new URI("api/email/emailById/1")))
		.andExpect(content().contentType(MediaTypes.HAL_JSON));
				
	}
	
}
