package com.example.forum_4_stupid.hateoas;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.forum_4_stupid.controller.UserController;
import com.example.forum_4_stupid.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(SpringRunner.class)
public class UserDtoAssemblerTest {

	
	private static UserDTO userDTO;
	
	@Before
	public void setUpUserDTO() {
		userDTO = new UserDTO(1, "test", 0, 0);
	}

	@Test
	public void shouldReturnAppropriateLinks() throws JsonProcessingException {
		userDTO.add(linkTo(methodOn(UserController.class)
				.getUserInformationById(userDTO.getId()))
				.withRel("userById"));
		
		userDTO.add(linkTo(methodOn(UserController.class)
				.getUserInformationByUsername(userDTO.getUsername()))
				.withRel("userByUsername"));
		
		MatcherAssert.assertThat("/api/user/userById/1", 
				equalTo(userDTO.getLink("userById").get().getHref()));
		MatcherAssert.assertThat("/api/user/userByUsername?username=test", 
				equalTo(userDTO.getLink("userByUsername").get().getHref()));
	}

}
