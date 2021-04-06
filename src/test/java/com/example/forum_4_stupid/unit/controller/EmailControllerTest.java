package com.example.forum_4_stupid.unit.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.forum_4_stupid.controller.EmailController;
import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.EmailDtoMapper;
import com.example.forum_4_stupid.hateoas.EmailDTOAssembler;

@ExtendWith(SpringExtension.class)
public class EmailControllerTest {

	private static EmailDTO emailDTO;
	private static EmailController controller;
	
	@MockBean
	private EmailDTOAssembler assembler;
	@MockBean
	private EmailDtoMapper mapper;
	
	@BeforeEach
	public void setUp() {
		controller = new EmailController(mapper, assembler);
		var userDTO = new UserDTO();
		userDTO.setId(1);
		userDTO.setUsername("test");
		userDTO.setTotalEmails(0);
		userDTO.setTotalTodos(0);
		emailDTO = new EmailDTO();
		emailDTO.setId(1);
		emailDTO.setEmail("test@gmail.com");
		emailDTO.setUser(userDTO);
	}
	
	@Test
	public void verifyEmailDTOAssemblerCalled() {
		when(mapper.getById(1)).thenReturn(emailDTO);
		when(assembler.toModel(emailDTO)).thenReturn(EntityModel.of(emailDTO));
		
		controller.getEmailById(1);
		verify(assembler).toModel(emailDTO);
	}
	
	@Test
	public void shouldReturnResponseEntityWithLinks() {
		when(mapper.getById(1)).thenReturn(emailDTO);
		when(assembler.toModel(emailDTO)).thenReturn(EntityModel.of(emailDTO));
		
		ResponseEntity<EntityModel<EmailDTO>> email = controller.getEmailById(1);
		System.out.println(email.getHeaders().getContentType());
		System.out.println(org.springframework.hateoas.MediaTypes.HAL_JSON);
		assertThat(email.getHeaders().getContentType(), 
				equalTo(org.springframework.hateoas.MediaTypes.HAL_JSON));
		
	}
}
