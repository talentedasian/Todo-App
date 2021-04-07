package com.example.forum_4_stupid.unit.hateoas;

import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.hateoas.EntityModel;

import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.hateoas.UserDTOAssembler;
import com.fasterxml.jackson.core.JsonProcessingException;

public class UserDtoAssemblerTest {

	private static UserDTOAssembler assembler;
	private static UserDTO userDTO;
	
	@BeforeEach
	public void setUp() {
		userDTO = new UserDTO(1,
				"test",
				0, 
				0);
		
		assembler = new UserDTOAssembler();
	}

	@org.junit.jupiter.api.Test
	public void shouldReturnAppropriateLinks() throws JsonProcessingException {
		EntityModel<UserDTO> entityModel = assembler.toModel(userDTO);
		
		MatcherAssert.assertThat("/api/user/userById/1", 
				equalTo(entityModel.getLink("userById").get().getHref()));
		MatcherAssert.assertThat("/api/user/userByUsername?username=test", 
				equalTo(entityModel.getLink("userByUsername").get().getHref()));
	}

}
