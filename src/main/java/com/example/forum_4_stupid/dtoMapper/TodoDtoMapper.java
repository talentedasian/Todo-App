package com.example.forum_4_stupid.dtoMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.dto.TodoDTO;
import com.example.forum_4_stupid.dto.TodoRequest;
import com.example.forum_4_stupid.dtoMapper.interfaces.TodoDTOMapper;
import com.example.forum_4_stupid.model.Todos;
import com.example.forum_4_stupid.service.TodoService;

@Component
public class TodoDtoMapper implements TodoDTOMapper<TodoDTO,TodoRequest,Todos>{

	@Autowired
	private TodoService todoService;

	@Override
	public TodoDTO getById(Integer id) {
		Todos todos = todoService.findTodosById(id);
		var todoDTO = new TodoDTO();
		todoDTO.setId(todos.getId());
		todoDTO.setContent(todos.getContent());
		todoDTO.setCreatedAt(todos.getCreated());
		todoDTO.setDeadline(todos.getDeadline());
		todoDTO.setTitle(todos.getTitle());
		
		return todoDTO;
	}

	@Override
	public void save(TodoRequest request) {
		todoService.addTodos(request);
	}

	@Override
	public void delete(Todos entity) {
		
	}

	@Override
	public List<TodoDTO> getAllByUserId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TodoDTO> getAllByUserUsername(String username) {
		List<Todos> todos = todoService.findAllTodosByOwnerUsername(username);
		List<TodoDTO> todoDTOResponse = new ArrayList<>();
		
		for(Todos todo : todos) {
			var todoDTO = new TodoDTO();
			todoDTO.setId(todo.getId());
			todoDTO.setContent(todo.getContent());
			todoDTO.setCreatedAt(todo.getCreated());
			
			todoDTOResponse.add(todoDTO);
		}
		
		return todoDTOResponse;
		
	}
	
}
