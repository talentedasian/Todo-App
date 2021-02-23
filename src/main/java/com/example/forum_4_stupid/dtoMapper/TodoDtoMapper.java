package com.example.forum_4_stupid.dtoMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.dto.TodoDTO;
import com.example.forum_4_stupid.dto.TodoRequest;
import com.example.forum_4_stupid.model.Todos;
import com.example.forum_4_stupid.service.TodoService;

@Component
public class TodoDtoMapper {
	
	@Autowired
	private TodoService todoService;
	
	public TodoDTO getTodos (Todos todos) {
		var todoDTO = new TodoDTO();
		todoDTO.setId(todos.getId());
		todoDTO.setContent(todos.getContent());
		todoDTO.setCreatedAt(todos.getCreated());
		todoDTO.setDeadline(todos.getDeadline());
		todoDTO.setTitle(todos.getTitle());
		
		return todoDTO;
	}

	public void addTodos (TodoRequest todoRequest) {
		todoService.addTodos(todoRequest);
	}
}
