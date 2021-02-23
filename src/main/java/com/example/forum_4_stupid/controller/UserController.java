package com.example.forum_4_stupid.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.dto.EmailRequest;
import com.example.forum_4_stupid.dto.TodoRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.EmailDtoMapper;
import com.example.forum_4_stupid.dtoMapper.UserDtoMapper;
import com.example.forum_4_stupid.model.Email;
import com.example.forum_4_stupid.model.Todos;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.service.EmailService;
import com.example.forum_4_stupid.service.TodoService;
import com.example.forum_4_stupid.service.UserService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	private final TodoService todoService;
	private final EmailService emailService;
	
	@Autowired
	public UserController (UserService userService, EmailService emailService, TodoService todoService) {
		this.userService = userService;
		this.emailService = emailService;
		this.todoService = todoService;
	}
	
	//implement posts endpoint first
	@GetMapping("/user/{id}")
	public ResponseEntity<UserDTO> getUserInformation (@PathVariable String id) throws NotFoundException {
		Users users = userService.findUserById(Integer.parseInt(id)).get();
		
		var user = new UserDtoMapper().returnUser(users);
		
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}
	
	@PostMapping("add-todo")
	public void addTodo (@ModelAttribute TodoRequest todoRequest) {
		todoService.addTodos(todoRequest);
	}
	
	
	@PostMapping("/add-email")
	public void addEmail (@ModelAttribute EmailRequest emailRequest) {
		emailService.addEmail(emailRequest);
	}
	
	@GetMapping("/email/{owner_id}")
	public ResponseEntity<EmailDTO> getEmail(@PathVariable String owner_id) {
		Users user = userService.findUserById(Integer.parseInt(owner_id)).get();
		Email email = userService.getEmail(owner_id).get(0);
		
		var emailDtoMapper = new EmailDtoMapper();
		EmailDTO emailResponse = emailDtoMapper.returnEmail(email);
		
		return new ResponseEntity<EmailDTO>(emailResponse, HttpStatus.OK);
	}
	
	
}
