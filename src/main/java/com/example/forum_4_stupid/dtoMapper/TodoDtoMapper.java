package com.example.forum_4_stupid.dtoMapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.dto.TodoDTO;
import com.example.forum_4_stupid.dto.TodoRequest;
import com.example.forum_4_stupid.dto.TodoSendStatusDTO;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.interfaces.DTOClassMapper;
import com.example.forum_4_stupid.dtoMapper.interfaces.TodoDTOMapper;
import com.example.forum_4_stupid.model.Todos;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.service.TodoService;

@Component
public class TodoDtoMapper implements TodoDTOMapper<TodoDTO,TodoRequest,Todos>
		,DTOClassMapper<UserDTO, Users>{

	private TodoService todoService;

	public TodoDtoMapper(TodoService todoService) {
		this.todoService = todoService;
	}

	@Override
	public TodoDTO getById(Integer id) {
		Todos todos = todoService.findTodosById(id);
		var todoDTO = new TodoDTO();
		todoDTO.setId(todos.getId());
		todoDTO.setContent(todos.getContent());
		todoDTO.setCreatedAt(todos.getCreated());
		todoDTO.setDeadline(todos.getDeadline());
		todoDTO.setTitle(todos.getTitle());
		todoDTO.setUser(mapEntityToDTO(todos.getUser()));
		todoDTO.setSendable(todos.isSendable());
		todoDTO.setTodoSendStatus(createTodoSendStatusDTOWhenGettingTodos(todos));
		
		return todoDTO;
	}

	@Override
	public TodoDTO save(TodoRequest request) {
		Todos todos = todoService.addTodos(request);
		
		var todoDTO = new TodoDTO();
		todoDTO.setId(todos.getId());
		todoDTO.setContent(todos.getContent());
		todoDTO.setCreatedAt(todos.getCreated());
		todoDTO.setDeadline(todos.getDeadline());
		todoDTO.setTitle(todos.getTitle());
		todoDTO.setUser(mapEntityToDTO(todos.getUser()));
		todoDTO.setSendable(todos.isSendable());
		todoDTO.setTodoSendStatus(createTodoSendStatusDTOWhenAddingTodo(request, todos));
		
		return todoDTO;
	}

	@Override
	public void delete(Todos entity) {
		
	}

	@Override
	public List<TodoDTO> getAllByUserId(Integer id) {
		List<Todos> todos = todoService.findAllTodosByOwnerId(id);
		List<TodoDTO> todoDTOResponse = returnListTodoDTO(todos);
		
		return todoDTOResponse;
	}

	@Override
	public List<TodoDTO> getAllByUserUsername(String username) {
		List<Todos> todos = todoService.findAllTodosByOwnerUsername(username);
		List<TodoDTO> todoDTOResponse = returnListTodoDTO(todos);
		
		return todoDTOResponse;
		
	}

	@Override
	public UserDTO mapEntityToDTO(Users entity) {
		var userDTO = new UserDTO();
		userDTO.setId(entity.getId());
		userDTO.setUsername(entity.getUsername());
		userDTO.setTotalPhoneNumbers(entity.getPhoneNumber().size());
		userDTO.setTotalTodos(entity.getTodos().size());
		
		return userDTO;
	}
	
	private List<TodoDTO> returnListTodoDTO(List<Todos> todos) {
		List<TodoDTO> todoDTOResponse = new ArrayList<>();
		
		for(Todos todo : todos) {
			var todoDTO = new TodoDTO();
			todoDTO.setId(todo.getId());
			todoDTO.setContent(todo.getContent());
			todoDTO.setCreatedAt(todo.getCreated());
			todoDTO.setUser(mapEntityToDTO(todo.getUser()));
			todoDTO.setSendable(todo.isSendable());
			todoDTO.setTodoSendStatus(createTodoSendStatusDTOWhenGettingTodos(todo));
			
			todoDTOResponse.add(todoDTO);
		}
		
		return todoDTOResponse;
	}
	
	private TodoSendStatusDTO createTodoSendStatusDTOWhenAddingTodo(TodoRequest todoRequest, Todos todo) {
		var todoSendStatusDTO = new TodoSendStatusDTO();
		if (todoRequest.isSendable()) {
			if(todo.getUser().getPhoneNumber().size() < 1) {
				todoSendStatusDTO.setGoingToBeSentOnDeadline(false);
				todoSendStatusDTO.setReason("No phone number to send to");
				
				return todoSendStatusDTO;
			} else {
				todoSendStatusDTO.setGoingToBeSentOnDeadline(true);
				
				return todoSendStatusDTO;
			}
		} else {
			todoSendStatusDTO.setGoingToBeSentOnDeadline(false);
			todoSendStatusDTO.setReason("Todo is not sendable");
			
			return todoSendStatusDTO;
		}
	}
	
	private TodoSendStatusDTO createTodoSendStatusDTOWhenGettingTodos(Todos todo) {
		var todoSendStatusDTO = new TodoSendStatusDTO();
		if (todo.isSendable()) {
			if(todo.getUser().getPhoneNumber().size() < 1) {
				todoSendStatusDTO.setGoingToBeSentOnDeadline(false);
				todoSendStatusDTO.setReason("No phone number to send to");
				
				return todoSendStatusDTO;
			} else {
				todoSendStatusDTO.setGoingToBeSentOnDeadline(true);
				
				return todoSendStatusDTO;
			}
		} else {
			todoSendStatusDTO.setGoingToBeSentOnDeadline(false);
			todoSendStatusDTO.setReason("Todo is not sendable");
			
			return todoSendStatusDTO;
		}		
	}
}
