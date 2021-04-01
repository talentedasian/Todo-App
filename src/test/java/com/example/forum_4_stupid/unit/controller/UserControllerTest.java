package com.example.forum_4_stupid.unit.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.forum_4_stupid.controller.UserController;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.UserDtoMapper;
import com.example.forum_4_stupid.hateoas.UserDTOAssembler;
import com.example.forum_4_stupid.utility.NestedDTOAssembler;

@RunWith(SpringRunner.class)
public class UserControllerTest {

	@MockBean
	private UserDtoMapper mapper;
	@MockBean
	private UserDTOAssembler assembler;

	@Test
	public void verifyUserDTOAssemblerCalled() {
		UserController userController = new UserController(mapper, assembler);
		var userDTO = new UserDTO(1, "test", 0, 0);
		
		Mockito.when(mapper.getById(1)).thenReturn(userDTO);
		userController.getUserInformationById(1);
		
		verify(assembler, times(1)).toModel(new UserDTO(1,
				"test", 0, 0));
	}
}
