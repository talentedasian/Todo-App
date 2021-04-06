package com.example.forum_4_stupid.unit.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.forum_4_stupid.controller.TodoController;
import com.example.forum_4_stupid.dto.TodoDTO;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.TodoDtoMapper;
import com.example.forum_4_stupid.hateoas.TodoDTOAssembler;

@ExtendWith(SpringExtension.class)
public class TodoControllerTest {

	@MockBean
	private static TodoDtoMapper mapper; 
	@MockBean
	private static TodoDTOAssembler assembler;
	private static TodoDTO todoDTO;
	private static LocalDateTime timeNow = LocalDateTime.now();
	private static TodoController controller;
	private static EntityModel<TodoDTO> entityModel;
	
	@BeforeEach
	public void setUp() {
		var userDTO = new UserDTO(1, "test", 0, 0);
		todoDTO = new TodoDTO(1, 
				"test content shit",
				"test title shit", 
				timeNow,
				LocalDateTime.of(2021, 5, 22, 12, 21),
				userDTO);
		
		controller = new TodoController(mapper, assembler);
		
		entityModel = EntityModel.of(todoDTO);
		entityModel.add(linkTo(methodOn(TodoController.class)
				.getTodoById(entityModel.getContent().getId()))
				.withSelfRel());
		entityModel.add(linkTo(methodOn(TodoController.class)
				.getTodoByUserId(entityModel.getContent().getUser().getId()))
				.withRel("inUserTodo"));
	}
	
	@Test
	public void verifyTodoDTOAssemblerCallToModel() {
		when(mapper.getById(1)).thenReturn(todoDTO);
		when(assembler.toModel(todoDTO)).thenReturn(entityModel);
		controller.getTodoById(1);
		verify(assembler).toModel(todoDTO);
	}
	
}
