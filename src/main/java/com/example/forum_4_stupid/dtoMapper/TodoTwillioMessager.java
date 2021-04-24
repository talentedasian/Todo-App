package com.example.forum_4_stupid.dtoMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.dto.TodoRequest;
import com.example.forum_4_stupid.exceptions.TodoNotSendableNoPhoneNumberAssociatedOnUser;
import com.example.forum_4_stupid.model.PhoneNumber;
import com.example.forum_4_stupid.service.PhoneService;
import com.example.forum_4_stupid.service.TodoService;
import com.example.forum_4_stupid.todoTwillioMessager.interfaces.TwillioMessager;

@Component
public class TodoTwillioMessager implements TwillioMessager{

	private final TodoService todoService;
	private final PhoneService phoneService;
	
	@Autowired
	public TodoTwillioMessager(TodoService todoService, PhoneService phoneService) {
		super();
		this.todoService = todoService;
		this.phoneService = phoneService;
	}

	@Override
	public void sendMessage(TodoRequest todoRequest) {
		List<PhoneNumber> phoneOfUsers = phoneService.getAllPhoneNumberFromUserByUsername(todoRequest.getUsername());
		if(phoneOfUsers.size() == 0) {
			throw new TodoNotSendableNoPhoneNumberAssociatedOnUser("No phone Number Associated on User");
		}
		
		todoService.sendMessagesByDeadlineTodos(todoRequest);
		
	}
	
}
