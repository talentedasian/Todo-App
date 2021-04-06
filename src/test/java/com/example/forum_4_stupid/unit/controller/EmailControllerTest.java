package com.example.forum_4_stupid.unit.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
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
}
