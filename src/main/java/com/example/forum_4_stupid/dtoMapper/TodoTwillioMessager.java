package com.example.forum_4_stupid.dtoMapper;

import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.dto.TodoRequest;
import com.example.forum_4_stupid.service.TodoService;
import com.example.forum_4_stupid.todoTwillioMessager.interfaces.TwillioMessager;

@Component
public class TodoTwillioMessager implements TwillioMessager{

	private final TodoService todoService;
	
	public TodoTwillioMessager(TodoService todoService) {
		super();
		this.todoService = todoService;
	}

	@Override
	public void sendMessage(TodoRequest todoRequest) {
		todoService.sendMessagesByByDeadlineTodos(todoRequest);
		
	}

	
}
