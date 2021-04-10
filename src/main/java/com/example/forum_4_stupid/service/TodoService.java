package com.example.forum_4_stupid.service;

import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.forum_4_stupid.dto.TodoRequest;
import com.example.forum_4_stupid.exceptions.TodoAlreadyExistException;
import com.example.forum_4_stupid.exceptions.TodoNotFoundException;
import com.example.forum_4_stupid.model.Todos;
import com.example.forum_4_stupid.repository.TodosRepository;
import com.example.forum_4_stupid.repository.UsersRepository;

@Service
@EnableTransactionManagement
@EnableAsync
public class TodoService {

	private final TodosRepository todosRepository;
	private final UsersRepository usersRepository;

	@Autowired
	public TodoService(TodosRepository todosRepository, UsersRepository usersRepository) {
		this.todosRepository = todosRepository;
		this.usersRepository = usersRepository;
	}
	
	public Todos addTodos (TodoRequest todoRequest) {
		try {
			var todos = new Todos();
			todos.setContent(todoRequest.getContent());
			todos.setTitle(todoRequest.getTitle());
			todos.setDeadline(todoRequest.getDeadline());
			todos.setUser(usersRepository.findByUsername(todoRequest.getUsername()).get());
			
			todosRepository.save(todos);
			
			return todos;
		} catch (DataIntegrityViolationException e) {
			throw new TodoAlreadyExistException("Todo Already Exist");	
		}
	}
	
	@Transactional(readOnly = true)
	public List<Todos> findAllTodosByOwnerId(Integer id) {
		try {
			List<Todos> todo = todosRepository.findByUser_Id(id);
			return todo;			
		} catch (NoSuchElementException e) {
			throw new TodoNotFoundException("Todo Does Not Exist");
		}
	}
	
	@Transactional(readOnly = true)
	public List<Todos> findAllTodosByOwnerUsername(String username) {
		try {
			List<Todos> todo = todosRepository.findByUser_Username(username);
			return todo;			
		} catch (NoSuchElementException e) {
			throw new TodoNotFoundException("Todo Does Not Exist");
		}
	}
	
	@Transactional(readOnly = true)
	public Todos findTodosById(Integer id) {
		Todos todo = todosRepository.findById(id)
				.orElseThrow(() -> new TodoNotFoundException("Todo Does Not Exist"));
		return todo;
	}
	
	@Async
	@Scheduled(fixedRate = 10000L)
	protected void deleteOldTodos() { 
		LocalDateTime timeNow = now();
		System.out.println(timeNow);
		
		CompletableFuture.supplyAsync(() -> todosRepository.findAll())
				.thenAccept((future) -> future.stream().
						filter((filteredTodo) -> filteredTodo.getDeadline().isAfter(timeNow))
						.forEach((deleteTodo) -> todosRepository.deleteById(deleteTodo.getId())));
		
		
	}
	
}
