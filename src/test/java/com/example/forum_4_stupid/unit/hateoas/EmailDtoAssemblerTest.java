package com.example.forum_4_stupid.unit.hateoas;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.hateoas.EmailDTOAssembler;
import com.example.forum_4_stupid.utility.NestedDTOAssembler;
import com.fasterxml.jackson.core.JsonProcessingException;

@ExtendWith(SpringExtension.class)
public class EmailDtoAssemblerTest {

	private static EmailDTO emailDTO;
	
	private static EmailDTOAssembler assembler;
	
	@BeforeEach
	public void setUp() {
		emailDTO = new EmailDTO();
		emailDTO.setId(2);
		emailDTO.setEmail("testemail@gmail.com");
		emailDTO.setUser(new UserDTO(1, "test", 1, 0));
		
		assembler = new EmailDTOAssembler();
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
