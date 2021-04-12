package com.example.forum_4_stupid.unit.hateoas;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.example.forum_4_stupid.dto.TodoDTO;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.hateoas.TodoDTOAssembler;
import com.example.forum_4_stupid.utility.NestedDTOAssembler;

public class TodoDtoAssemblerTest {

	private static TodoDTO todoDTO;
	
	private static TodoDTOAssembler assembler;

	
	@BeforeEach	
	public void setUp() {
		todoDTO = new TodoDTO(1, 
				"test content", 
				"test title", 
				java.time.LocalDateTime.now(),
				java.time.LocalDateTime.of(2021, 5, 5, 3, 2), 
				new UserDTO(1, "test", 0, 1));
		
		assembler = new TodoDTOAssembler();
	}

	@org.junit.jupiter.api.Test
	public void shouldReturnEntityModelExpectedLinks() {
		EntityModel<TodoDTO> nestedEntityModel = assembler.toModel(todoDTO);
		NestedDTOAssembler.addUserFromTodoNestedEntityLink(nestedEntityModel);
		
		assertThat("/api/todo/todoById/1", 
				equalTo(nestedEntityModel.getLink("self").get().getHref()));
		assertThat("/api/todo/todoByOwnerId?id=1", 
				equalTo(nestedEntityModel.getLink("inUserTodo").get().getHref()));
		assertThat("/api/user/userById/1", 
				equalTo(nestedEntityModel.getContent().getUser().getLink("inUserById").get().getHref()));
		assertThat("/api/user/userByUsername?username=test", 
				equalTo(nestedEntityModel.getContent().getUser().getLink("inUserByUsername").get().getHref()));
		
	}
	
	@org.junit.jupiter.api.Test
	public void shouldReturnCollectionModelExpectedLinks() {
		List<TodoDTO> listTodoDTO = new ArrayList<>();
		listTodoDTO.add(todoDTO);
		CollectionModel<EntityModel<TodoDTO>> collectionModel = assembler.toCollectionModel(listTodoDTO);
		NestedDTOAssembler.addUserFromTodoNestedEntityLink(collectionModel);
		
		for (EntityModel<TodoDTO> entityModel : collectionModel) {			
			assertThat("/api/todo/todoById/1", 
					equalTo(entityModel.getLink("self").get().getHref()));
			assertThat("/api/todo/todoByOwnerId?id=1", 
					equalTo(entityModel.getLink("inUserTodo").get().getHref()));
			assertThat("/api/user/userById/1", 
					equalTo(entityModel.getContent().getUser().getLink("inUserById").get().getHref()));
			assertThat("/api/user/userByUsername?username=test", 
					equalTo(entityModel.getContent().getUser().getLink("inUserByUsername").get().getHref()));
			
		}
		
		assertThat("/api/todo/todoByOwnerId?id=1", 
				equalTo(collectionModel.getLink("self").get().getHref()));
	}
	
}
