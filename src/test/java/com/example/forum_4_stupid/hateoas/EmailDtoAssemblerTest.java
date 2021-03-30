package com.example.forum_4_stupid.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.forum_4_stupid.controller.UserController;
import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.utility.NestedDTOAssembler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = EmailDTOAssembler.class)
public class EmailDtoAssemblerTest {

	private static EmailDTO emailDTO1;
	private static EmailDTO emailDTO2;
	
	@Autowired
	private EmailDTOAssembler assembler;
	
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
	}
	
	@Test
	public void shouldReturnExpectedLinks() throws JsonProcessingException {
		List<EmailDTO> entityModel = new ArrayList<>();
		entityModel.add(emailDTO1);
		
		CollectionModel<EntityModel<EmailDTO>> collectionModel = assembler.toCollectionModel(entityModel);
		
		
		for (EntityModel<EmailDTO> entityModel2 : collectionModel) {
			entityModel2.getContent().getUser().add(linkTo(methodOn(UserController.class)
					.getUserInformationById(entityModel2.getContent().getUser().getId()))
					.withRel("userById"));
			
			entityModel2.getContent().getUser().add(linkTo(methodOn(UserController.class)
					.getUserInformationByUsername(entityModel2.getContent().getUser().getUsername()))
					.withRel("userByUsername"));
			
			assertThat("/api/user/userById/1", 
				equalTo(entityModel2.getContent().getUser().getLink("userById").get().getHref()));
			
			assertThat("/api/user/userByUsername?username=test", 
					equalTo(entityModel2.getContent().getUser().getLink("userByUsername").get().getHref()));
			
			assertThat("/api/email/emailById?id=2", 
					equalTo(entityModel2.getLink("self").get().getHref()));
			
			assertThat("/api/email/emailByOwnerId/1", 
					equalTo(entityModel2.getLink("inUserEmail").get().getHref()));
			
		}
		
		assertThat("/api/email/emailByOwnerId/1", 
				equalTo(collectionModel.getLink("self").get().getHref()));
		
		
	}	
	
}
