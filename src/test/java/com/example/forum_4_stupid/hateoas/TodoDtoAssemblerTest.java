package com.example.forum_4_stupid.hateoas;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.forum_4_stupid.dto.TodoDTO;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.utility.NestedDTOAssembler;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TodoDTOAssembler.class)
public class TodoDtoAssemblerTest {

	private static TodoDTO todoDTO;
	
	@Autowired
	private TodoDTOAssembler assembler;

	
	@Before
	public void setUp() {
		todoDTO = new TodoDTO(1, 
				"test content", 
				"test title", 
				java.time.LocalDateTime.now(),
				java.time.LocalDateTime.of(2021, 5, 5, 3, 2), 
				new UserDTO(1, "test", 0, 1));
		
	}

	@Test
	public void shouldReturnEntityModelExpectedLinks() {
		var nestedDtoAssembler = new NestedDTOAssembler();
		EntityModel<TodoDTO> nestedEntityModel = new TodoDTOAssembler().toModel(todoDTO);
		nestedDtoAssembler.addUserFromTodoNestedEntityLink(nestedEntityModel);
		
		assertThat("/api/todo/todoById/1", 
				equalTo(nestedEntityModel.getLink("self").get().getHref()));
		assertThat("/api/todo/todoByOwnerId?id=1", 
				equalTo(nestedEntityModel.getLink("inUserTodo").get().getHref()));
		assertThat("/api/user/userById/1", 
				equalTo(nestedEntityModel.getContent().getUser().getLink("inUserById").get().getHref()));
		assertThat("/api/user/userByUsername?username=test", 
				equalTo(nestedEntityModel.getContent().getUser().getLink("inUserByUsername").get().getHref()));
		
	}
}
