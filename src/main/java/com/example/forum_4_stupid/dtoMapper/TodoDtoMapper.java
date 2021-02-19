package com.example.forum_4_stupid.dtoMapper;

import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.dto.TodoDTO;
import com.example.forum_4_stupid.model.Todos;

@Component
public class TodoDtoMapper {
	
	public TodoDTO getTodos (Todos todos) {
		var todoDTO = new TodoDTO();
		todoDTO.setId(todos.getId());
		todoDTO.setContent(todos.getContent());
		todoDTO.setCreatedAt(todos.getCreated());
		todoDTO.setDeadline(todos.getDeadline());
		todoDTO.setTitle(todos.getTitle());
		
		return todoDTO;
	}
	
	public TodoDTO getForeignKeyTodo (Todos todos) {
		var todoDTO = new TodoDTO();
		todoDTO.setId(todos.getId());
		
		return todoDTO;
	}

}
