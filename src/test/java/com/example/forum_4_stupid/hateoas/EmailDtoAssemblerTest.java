package com.example.forum_4_stupid.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.forum_4_stupid.controller.EmailController;
import com.example.forum_4_stupid.controller.UserController;
import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = EmailDTOAssembler.class)
public class EmailDtoAssemblerTest {

	private static EmailDTO emailDTO1;
	private static EmailDTO emailDTO2;
	private static EntityModel<EmailDTO> entityModel;
	private static RepresentationModel<?> collectionModel;
	
	@Before
	public void setUpEmailDTO() {
		emailDTO1 = new EmailDTO();
		emailDTO1.setId(2);
		emailDTO1.setEmail("testemail@gmail.com");
		emailDTO1.setUser(new UserDTO(1, "test", 1, 0));
		
		emailDTO2 = new EmailDTO();
		emailDTO2.setId(3);
		emailDTO2.setEmail("testemail2@gmail.com");
		emailDTO2.setUser(new UserDTO(1, "test", 2, 0));
		
		entityModel = EntityModel.of(emailDTO2);
		
		entityModel.add(linkTo(methodOn(EmailController.class)
				.getEmailById(entityModel.getContent().getId()))
				.withSelfRel());
	
		entityModel.add(linkTo(methodOn(EmailController.class)
				.getEmailByOwnerId(entityModel.getContent().getUser().getId()))
				.withRel("inUserEmail"));
		
		entityModel.getContent().getUser().add(linkTo(methodOn(UserController.class)
				.getUserInformationById(emailDTO2.getUser().getId()))
				.withRel("inUserById"));
		
		entityModel.getContent().getUser().add(linkTo(methodOn(UserController.class)
				.getUserInformationByUsername(emailDTO2.getUser().getUsername()))
				.withRel("inUserByUsername"));
		
		collectionModel = CollectionModel.of(entityModel);
		
		collectionModel.add(linkTo(methodOn(EmailController.class).getEmailByOwnerId(emailDTO2.getUser().getId()))
				.withSelfRel());
	}
	
	
	@Test
	public void shouldReturnExpectedLinks() throws JsonProcessingException {
		assertThat("/api/email/emailByOwnerId/1", 
				equalTo(collectionModel.getLink("self").get().getHref()));
		
	}	
	
	@Test
	public void shouldReturnEntityModelLinks() {
		assertThat("/api/email/emailByOwnerId/1", 
				equalTo(entityModel.getLink("inUserEmail").get().getHref()));
		assertThat("/api/email/emailById?id=3", 
				equalTo(entityModel.getLink("self").get().getHref()));
		assertThat("/api/user/userById/1", 
				equalTo(entityModel.getContent().getUser().getLink("inUserById").get().getHref()));
		assertThat("/api/user/userByUsername?username=test", 
				equalTo(entityModel.getContent().getUser().getLink("inUserByUsername").get().getHref()));
		
	}
	
}
