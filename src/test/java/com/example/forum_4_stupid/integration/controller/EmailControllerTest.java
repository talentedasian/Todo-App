package com.example.forum_4_stupid.integration.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URISyntaxException;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
@AutoConfigureMockMvc(print = MockMvcPrint.DEFAULT, addFilters = false, printOnlyOnFailure = false)
public class EmailControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private EmailRepository emailRepo;
	@MockBean
	private UsersRepository userRepo;
	@MockBean
	private EmailDTOAssembler assembler;
	@MockBean
	private EmailDtoMapper mapper;
	
	private static EmailDTO emailDTO;
	private static EntityModel<EmailDTO> entityModel;
	
	@BeforeEach
	public void setUp() {
		var userDTO = new UserDTO(1, "testusername", 1, 0);
		emailDTO = new EmailDTO();
		emailDTO.setId(1);
		emailDTO.setEmail("test@gmail.com");
		emailDTO.setUser(userDTO);
		
		entityModel = EntityModel.of(emailDTO);
		entityModel.add(linkTo(methodOn(EmailController.class)
				.getEmailById(entityModel.getContent().getId()))
			.withSelfRel());
	
		entityModel.add(linkTo(methodOn(EmailController.class)
			.getEmailByOwnerId(entityModel.getContent().getUser().getId()))
		.withRel("inUserEmail"));
	}	
	
	@Test
	public void shouldReturnHal_JsonContentType() throws URISyntaxException, Exception { 
		when(mapper.getById(1)).thenReturn(emailDTO);
		when(assembler.toModel(emailDTO)).thenReturn(EntityModel.of(emailDTO));
		mvc.perform(get(new URI("/api/email/emailById?id=1")))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaTypes.HAL_JSON));
				
	}
	
	@Test
	@DisplayName("Should ReturnExpectedJsonOutput When GetMappingEmailById")
	public void shouldExpectedDtoOutputs() throws URISyntaxException, Exception { 
		when(mapper.getById(1)).thenReturn(emailDTO);
		
		when(assembler.toModel(emailDTO)).thenReturn(EntityModel.of(emailDTO));
		
		mvc.perform(get(new URI("/api/email/emailById?id=1")))
		.andExpect(MockMvcResultMatchers.jsonPath("id", equalTo(emailDTO.getId())))
		.andExpect(MockMvcResultMatchers.jsonPath("email", equalTo(emailDTO.getEmail())))
		.andExpect(MockMvcResultMatchers.jsonPath("user.id", equalTo(emailDTO.getUser().getId())))
		.andExpect(MockMvcResultMatchers.jsonPath("user.username", equalTo(emailDTO.getUser().getUsername())))
		.andExpect(MockMvcResultMatchers.jsonPath("user.totalEmails", equalTo(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("user.totalTodos", equalTo(0)))
		.andExpect(MockMvcResultMatchers.jsonPath("_links.emailById.href", equalTo("/api/emailById?id=1")));
	}
	
}
