package com.example.forum_4_stupid.unit.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
	private static EntityModel<EmailDTO> entityModel;
	
	@Mock
	private EmailDTOAssembler assembler;
	@Mock
	private EmailDtoMapper mapper;
	
	@BeforeEach
	public void setUp() {
		controller = new EmailController(mapper, assembler);
		
		var userDTO = new UserDTO();
		userDTO.setId(1);
		userDTO.setUsername("testusername");
		userDTO.setTotalEmails(0);
		userDTO.setTotalTodos(0);
		emailDTO = new EmailDTO();
		emailDTO.setId(1);
		emailDTO.setEmail("test@gmail.com");
		emailDTO.setUser(userDTO);
		
		entityModel = EntityModel.of(emailDTO);
		entityModel.add(linkTo(methodOn(EmailController.class)
				.getEmailById(emailDTO.getId()))
			.withSelfRel());
	
		entityModel.add(linkTo(methodOn(EmailController.class)
			.getEmailByOwnerId(emailDTO.getUser().getId()))
		.withRel("inUserEmail"));
		
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
		assertThat(email.getHeaders().getContentType(), 
				equalTo(org.springframework.hateoas.MediaTypes.HAL_JSON));
		
	}
	
	@Test
	public void shouldReturnExpectedLinks() {
		when(mapper.getById(1)).thenReturn(emailDTO);
		
		when(assembler.toModel(emailDTO)).thenReturn(entityModel);
		
		ResponseEntity<EntityModel<EmailDTO>> email = controller.getEmailById(1);
		
		assertThat("/api/email/emailById?id=1", 
				equalTo(email.getBody().getLink("self").get().getHref()));
		assertThat("/api/email/emailByOwnerId/1", 
				equalTo(email.getBody().getLink("inUserEmail").get().getHref()));
		
	}
	
	@Test
	public void shouldReturnNestedUserDTOLinks() {
		when(mapper.getById(1)).thenReturn(emailDTO);
		
		when(assembler.toModel(emailDTO)).thenReturn(entityModel);
		
		ResponseEntity<EntityModel<EmailDTO>> email = controller.getEmailById(1);
		
		assertThat("/api/user/userById/1", 
				equalTo(email.getBody().getContent().getUser().getLink("inUserById").get().getHref()));
		assertThat("/api/user/userByUsername?username=testusername", 
				equalTo(email.getBody().getContent().getUser().getLink("inUserByUsername").get().getHref()));
	}
			
}