package com.example.forum_4_stupid.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.forum_4_stupid.dto.TodoRequest;
import com.example.forum_4_stupid.model.Todos;
import com.example.forum_4_stupid.repository.TodosRepository;
import com.example.forum_4_stupid.repository.UsersRepository;

@Service
@EnableTransactionManagement
public class TodoService {

	private final TodosRepository todosRepository;
	private final UsersRepository usersRepository;

	@Autowired
	public TodoService(TodosRepository todosRepository, UsersRepository usersRepository) {
		this.todosRepository = todosRepository;
		this.usersRepository = usersRepository;
	}
	
	public void addTodos (TodoRequest todoRequest) {
		var todos = new Todos();
		todos.setContent(todoRequest.getContent());
		todos.setTitle(todoRequest.getTitle());
		todos.setDeadline(todoRequest.getDeadline());
		todos.setCreated(new Date());
		todos.setCreator(usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get());
		
		todosRepository.save(todos);
	}
	
	@Transactional(readOnly = true)
	public Optional<Todos> findTodosByOwnerId (Integer id) {
		Optional<Todos> todo = Optional.of(todosRepository.findById(id)).get();
		
		return todo;
	}
	
}
