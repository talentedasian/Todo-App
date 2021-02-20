package com.example.forum_4_stupid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.TodoRequest;
import com.example.forum_4_stupid.service.TodoService;

@RestController
@RequestMapping("/todo")
public class TodoController {

	private final TodoService todoService;
	
	@Autowired
	public TodoController (TodoService todoService) {
		this.todoService = todoService;
	}
	
	//add appropriate redirects
	@PostMapping("/add-todo")
	public void addTodo (@ModelAttribute TodoRequest todoRequest) {
		todoService.addTodos(todoRequest);
	}
	
}
