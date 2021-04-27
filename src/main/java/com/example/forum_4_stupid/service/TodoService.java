package com.example.forum_4_stupid.service;

import static java.time.LocalDateTime.now;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
import com.example.forum_4_stupid.exceptions.TodoNotSendableDueToYearException;
import com.example.forum_4_stupid.exceptions.TodoNotSendableNoPhoneNumberAssociatedOnUser;
import com.example.forum_4_stupid.model.Todos;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.TodosRepository;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.todoSmsSend.SendTodoMessages;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

@Service
@EnableTransactionManagement
@EnableAsync
public class TodoService {

	private final TodosRepository todosRepository;
	private final UsersRepository usersRepository;
	private final PhoneNumber numberFrom;

	@Autowired
	public TodoService(TodosRepository todosRepository, UsersRepository usersRepository, PhoneNumber numberFrom) {
		this.todosRepository = todosRepository;
		this.usersRepository = usersRepository;
		this.numberFrom = numberFrom;
	}
	
	public Todos addTodos (TodoRequest todoRequest) {
		try {
			if (todoRequest.getYear() != 0 && todoRequest.getYear() < 2021) {
				throw new DateTimeException("Year cannot be lower than 2021. 0 is the only number accepeted to be lower than 2021", new TodoNotSendableDueToYearException());
			}
			var todos = new Todos(todoRequest.getYear(), todoRequest.getMonth(), todoRequest.getDay()
					, todoRequest.getHour(), todoRequest.getMinute());
			todos.setContent(todoRequest.getContent());
			todos.setTitle(todoRequest.getTitle());
			todos.setSendable(todoRequest.isSendable());
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
						filter((filteredTodo) -> filteredTodo.getDeadline().isAfter(timeNow) ||
								filteredTodo.getDeadline().isEqual(timeNow) ||
								filteredTodo.getDeadline().compareTo(timeNow) < 0)
						.forEach((deleteTodo) -> todosRepository.deleteById(deleteTodo.getId())));
		
	}
	
	@Async
	public void sendMessagesByDeadlineTodos(TodoRequest todoRequest) {
		Users user = usersRepository.findByUsername(todoRequest.getUsername()).get();
		
		String messageToBeSentPrefix = "Hoy pukinginamo gawin mo na ung ";
		
		if(todoRequest.isSendable()) {
			if(todoRequest.getYear() < 2021) {
				throw new TodoNotSendableDueToYearException("Todo has year before 2021");
			} else if(user.getPhoneNumber().size() == 0) {
				throw new TodoNotSendableNoPhoneNumberAssociatedOnUser("User has no available phone numbers to send sms messages to"); 
			}
		}
		
		todoRequest.setDeadline(LocalDateTime.of(
				todoRequest.getYear(), 
				todoRequest.getMonth(), 
				todoRequest.getDay(),
				todoRequest.getHour(), 
				todoRequest.getMinute()));
		
		MessageCreator message = new MessageCreator(new PhoneNumber(user.getPhoneNumber().get(0).getPhoneNumber()),
				numberFrom, 
				messageToBeSentPrefix + todoRequest.getTitle() + " sa " + 
				todoRequest.getDeadline().toLocalDate() + " na yan");
		
		SendTodoMessages.sendMessages(message, 
				Date.from(LocalDateTime.of(
						todoRequest.getYear(), 
						todoRequest.getMonth(), 
						todoRequest.getDay(),
						todoRequest.getHour() - 1, 
						todoRequest.getMinute())
					.atZone(ZoneId.systemDefault()).toInstant()));
	}
	
}
