package com.example.forum_4_stupid.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
			todos.setCreated(new Date());
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
		try {
			Todos todo = todosRepository.findById(id).get();
			return todo;			
		} catch (NoSuchElementException e) {
			throw new TodoNotFoundException("Todo Does Not Exist");
		}
	}
	
}
