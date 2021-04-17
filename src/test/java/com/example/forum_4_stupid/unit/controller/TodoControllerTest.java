package com.example.forum_4_stupid.unit.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.forum_4_stupid.controller.TodoController;
import com.example.forum_4_stupid.dto.TodoDTO;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.TodoDtoMapper;
import com.example.forum_4_stupid.dtoMapper.TodoTwillioMessager;
import com.example.forum_4_stupid.hateoas.TodoDTOAssembler;
import com.example.forum_4_stupid.service.TodoService;
import com.twilio.type.PhoneNumber;

@ExtendWith(SpringExtension.class)
public class TodoControllerTest {

	@Mock
	private static TodoService service;
	@Mock
	private static TodoDTOAssembler assembler;
	@Mock
	private static TodoDtoMapper mapper;
	@Mock
	private TodoTwillioMessager messager;
	
	private static TodoController controller;
	private static TodoDTO todoDTO;
	private static LocalDateTime timeNow = LocalDateTime.now();
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
		
		
		entityModel = EntityModel.of(todoDTO);
		entityModel.add(linkTo(methodOn(TodoController.class)
				.getTodoById(entityModel.getContent().getId()))
				.withSelfRel());
		entityModel.add(linkTo(methodOn(TodoController.class)
				.getTodoByUserId(entityModel.getContent().getUser().getId()))
				.withRel("inUserTodo"));
		
		controller = new TodoController(mapper, assembler, messager);
	}
	

	@Test
	public void verifyTodoDTOAssemblerCallToModel() {
		when(mapper.getById(1)).thenReturn(todoDTO);
		when(assembler.toModel(todoDTO)).thenReturn(entityModel);
		controller.getTodoById(1);
		
		verify(assembler).toModel(todoDTO);
	}
	
	@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
	@Test
	@DisplayName("Should Return Hal_Json As Media Type")
	public void shouldReturnHal_Json() {
		when(mapper.getById(1)).thenReturn(todoDTO);
		when(assembler.toModel(todoDTO)).thenReturn(entityModel);
		ResponseEntity<EntityModel<TodoDTO>> todo = controller.getTodoById(1);
		
		assertThat(todo.getHeaders().getContentType(), 
				equalTo(MediaTypes.HAL_JSON));
	}
	
	
}
