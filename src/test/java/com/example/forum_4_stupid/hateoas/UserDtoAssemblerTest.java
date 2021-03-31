package com.example.forum_4_stupid.hateoas;

import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.forum_4_stupid.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = UserDTOAssembler.class)
public class UserDtoAssemblerTest {

	@Autowired
	private UserDTOAssembler assembler;
	private static UserDTO userDTO;
	
	@Before
	public void setUpUserDTO() {
		userDTO = new UserDTO(1,
				"test",
				0, 
				0);
	}

	@Test
	public void shouldReturnAppropriateLinks() throws JsonProcessingException {
		EntityModel<UserDTO> entityModel = assembler.toModel(userDTO);
		
		MatcherAssert.assertThat("/api/user/userById/1", 
				equalTo(entityModel.getLink("userById").get().getHref()));
		MatcherAssert.assertThat("/api/user/userByUsername?username=test", 
				equalTo(entityModel.getLink("userByUsername").get().getHref()));
	}

}
