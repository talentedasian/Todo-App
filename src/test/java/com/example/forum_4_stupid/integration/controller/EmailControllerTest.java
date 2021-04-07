package com.example.forum_4_stupid.integration.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.forum_4_stupid.controller.EmailController;
import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.EmailDtoMapper;
import com.example.forum_4_stupid.hateoas.EmailDTOAssembler;
import com.example.forum_4_stupid.repository.EmailRepository;
import com.example.forum_4_stupid.repository.UsersRepository;

@WebMvcTest(controllers = EmailController.class)
@AutoConfigureMockMvc(print = MockMvcPrint.DEFAULT, addFilters = false)
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
	
	private static EmailDTO emailDTO;
	
	@BeforeEach
	public void setUp() {
		var userDTO = new UserDTO(1, "testusername", 1, 0);
		emailDTO = new EmailDTO();
		emailDTO.setId(1);
		emailDTO.setEmail("test@gmail.com");
		emailDTO.setUser(userDTO);
	}	
	
	@Test
	public void shouldReturnHal_JsonContentType() throws URISyntaxException, Exception { 
		when(mapper.getById(1)).thenReturn(emailDTO);
		when(assembler.toModel(emailDTO)).thenReturn(EntityModel.of(emailDTO));
		mvc.perform(get(new URI("/api/email/emailById?id=1")))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaTypes.HAL_JSON));
				
	}
	
}
