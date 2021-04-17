package com.example.forum_4_stupid.dtoMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.dto.TodoRequest;
import com.example.forum_4_stupid.service.TodoService;
import com.example.forum_4_stupid.todoTwillioMessager.interfaces.TwillioMessager;

@Component
@Configuration
public class TodoTwillioMessager implements TwillioMessager{

//	@Bean
//	@value
	
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
