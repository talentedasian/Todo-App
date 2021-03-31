package com.example.forum_4_stupid.hateoas;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.utility.NestedDTOAssembler;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = EmailDTOAssembler.class)
public class EmailDtoAssemblerTest {

	private static EmailDTO emailDTO;
	
	@Autowired
	private EmailDTOAssembler assembler;
	
	@Before
	public void setUpEmailDTO() {
		emailDTO = new EmailDTO();
		emailDTO.setId(2);
		emailDTO.setEmail("testemail@gmail.com");
		emailDTO.setUser(new UserDTO(1, "test", 1, 0));
	}
	
	
	@Test
	public void shouldReturnEntityModelExpectedLinks() throws JsonProcessingException {
		EntityModel<EmailDTO> entityModel = assembler.toModel(emailDTO);
		assertThat("/api/email/emailById?id=2", 
				equalTo(entityModel.getLink("self").get().getHref()));
		
	}	
	
	@Test
	public void shouldReturnCollcetionModelExpectedLinks() {
		List<EmailDTO> listEmailDTO = new ArrayList<>();
		listEmailDTO.add(emailDTO);
		CollectionModel<EntityModel<EmailDTO>> collectionModel = assembler.toCollectionModel(listEmailDTO);
		var nestedDtoAssembler = new NestedDTOAssembler();
		nestedDtoAssembler.addUserFromEmailNestedEntityLink(collectionModel);
		
		assertThat("/api/email/emailByOwnerId/1", 
				equalTo(collectionModel.getLink("self").get().getHref()));
		
		for (EntityModel<EmailDTO> entityModel : collectionModel) {
			assertThat("/api/user/userById/1", 
				equalTo(entityModel.getContent().getUser().getLink("inUserById").get().getHref()));
			assertThat("/api/user/userByUsername?username=test", 
				equalTo(entityModel.getContent().getUser().getLink("inUserByUsername").get().getHref()));			
		}
				
		
	}
	
}
