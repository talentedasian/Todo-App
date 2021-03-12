package com.example.forum_4_stupid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api/todo")
public class TodoController {

	private final TodoDtoMapper todoDtoMapper;
	
	@Autowired
	public TodoController (TodoDtoMapper todoDtoMapper) {
		this.todoDtoMapper = todoDtoMapper;
	}
	
	//add appropriate redirects
	@PostMapping("/add-todo")
	public void addTodo (@ModelAttribute TodoRequest todoRequest) {
		
		todoDtoMapper.save(todoRequest);
	}
	
	@GetMapping("todo")
	public ResponseEntity<List<TodoDTO>> getTodoByUserId (@RequestParam String id) {
		List<TodoDTO> response = todoDtoMapper.getAllByUserId(Integer.parseInt(id));
		
		return new ResponseEntity<List<TodoDTO>>(response, HttpStatus.OK);	
	}
	
}
