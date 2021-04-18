package com.example.forum_4_stupid.dtoMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.dto.TodoRequest;
import com.example.forum_4_stupid.service.TodoService;
import com.example.forum_4_stupid.todoTwillioMessager.interfaces.TwillioMessager;
import com.twilio.type.PhoneNumber;

@Component
@Configuration
public class TodoTwillioMessager implements TwillioMessager{

	@Value("${twillio.phone.number}")
	private String phoneNumber;
	
	@Bean
	public PhoneNumber number() {
		return new PhoneNumber(phoneNumber);
	}
	
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
