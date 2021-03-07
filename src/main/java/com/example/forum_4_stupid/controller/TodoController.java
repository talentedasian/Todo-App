package com.example.forum_4_stupid.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.TodoDTO;
import com.example.forum_4_stupid.dto.TodoRequest;
import com.example.forum_4_stupid.dtoMapper.TodoDtoMapper;
import com.example.forum_4_stupid.service.TodoService;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

	private final TodoService todoService;
	private final TodoDtoMapper todoDtoMapper;
	
	@Autowired
	public TodoController (TodoService todoService, TodoDtoMapper todoDtoMapper) {
		this.todoService = todoService;
		this.todoDtoMapper = todoDtoMapper;
	}
	
	//add appropriate redirects
	@PostMapping("/add-todo")
	public void addTodo (@ModelAttribute TodoRequest todoRequest) {
		
		todoDtoMapper.save(todoRequest);
	}
	
	@GetMapping("todo")
	public ResponseEntity<TodoDTO> getTodo (@RequestParam String id) {
		var todo = todoService.findTodosByOwnerId(Integer.parseInt(id));
		var response = todoDtoMapper.returnEntity(todo);
		
		return new ResponseEntity<TodoDTO>(response, new HttpHeaders(), HttpStatus.OK);	
	}
	
}
