package com.example.forum_4_stupid.unit.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.forum_4_stupid.dto.TodoRequest;
import com.example.forum_4_stupid.exceptions.TodoNotFoundException;
import com.example.forum_4_stupid.model.Todos;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.TodosRepository;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.service.TodoService;

@ExtendWith(SpringExtension.class)
public class TodoServiceTest {

	private static TodoService service;
	private static Users user;
	private static TodoRequest todoRequest;
	@MockBean
	private TodosRepository todosRepo;
	@MockBean
	private UsersRepository userRepo;
	
	@BeforeEach
	public void setUp() {
		//entity
		user = new Users(1, 
				"test", 
				LocalDateTime.now(), 
				"testpassword",
				true,
				null, 
				null);
		
		//service
		service = new TodoService(todosRepo, userRepo);
		
		//dto
		todoRequest = new TodoRequest();
		todoRequest.setTitle("test title");
		todoRequest.setUsername("test");
		todoRequest.setContent("test content shit");
		todoRequest.setYear(2021);
		todoRequest.setMonth(4);
		todoRequest.setDay(21);
		todoRequest.setHour(8);
		todoRequest.setMinute(22);
		todoRequest.setDeadline();
	}
	
	@Test
	public void verifyTodosRepositoryFindByIdCalled() {
		Optional<Todos> todos = Optional.of(new Todos(1,
				"test content", 
				"test title",
				LocalDateTime.of(2021, 4, 5, 2, 54),
				LocalDateTime.now(), 
				user));
		when(todosRepo.findById(1)).thenReturn(todos);
		service.findTodosById(1);
		verify(todosRepo, times(1)).findById(1);
	}
	
	@Test
	public void verifyTodosRepositorySaveCalled() {
		Optional<Todos> todos = Optional.of(new Todos(null,
				"test content shit", 
				"test title",
				LocalDateTime.of(2021, 4, 21, 8, 22),
				LocalDateTime.now(), 
				user));
		when(userRepo.findByUsername("test")).thenReturn(Optional.of(user));
		ArgumentCaptor<Todos> captor = ArgumentCaptor.forClass(Todos.class);
		when(todosRepo.save(todos.get())).thenReturn(todos.get());
		service.addTodos(todoRequest);
		verify(todosRepo, times(1)).save(captor.capture());
		verify(userRepo, times(1)).findByUsername("test");
		
		assertThat(captor.getValue().getUser(), equalTo(user));	
	}
	
	@Test
	public void shouldThrowTodoNotFoundException() {
		Optional<Todos> todos = Optional.of(new Todos(null,
				"test content shit", 
				"test title",
				LocalDateTime.of(2021, 4, 21, 8, 22),
				LocalDateTime.now(), 
				user));
		when(todosRepo.findById(2)).thenReturn(todos);
		
		assertThrows(TodoNotFoundException.class, () -> service.findTodosById(1));
	}
	

	@Test
	public void todosShouldEqualToUserPassed() {
		Optional<Todos> todos = Optional.of(new Todos(null,
				"test content shit", 
				"test title",
				LocalDateTime.of(2021, 4, 21, 8, 22),
				LocalDateTime.now(), 
				user));
		Optional<Todos> todos2 = Optional.of(new Todos(null,
				"test content shit", 
				"test title",
				LocalDateTime.of(2021, 4, 21, 8, 22),
				LocalDateTime.now(), 
				user));
		List<Todos> listOfTodosToReturn = Arrays.asList(todos.get(),todos2.get());
		when(todosRepo.findByUser_Username("test")).thenReturn(listOfTodosToReturn);
		
		assertThat(service.findAllTodosByOwnerUsername("test").get(0).getUser(),
				equalTo(user));
		assertThat(service.findAllTodosByOwnerUsername("test").get(1).getUser(),
				equalTo(user));
	}
	
	@Test
	public void todosDeadlineShouldEqualToDeadlinePassed() {
		Optional<Todos> todos = Optional.of(new Todos(null,
				"test content shit", 
				"test title",
				LocalDateTime.of(2021, 4, 21, 8, 22),
				LocalDateTime.now(), 
				user));
		when(todosRepo.findById(1)).thenReturn(todos);
		
		assertThat(LocalDateTime.of(2021, 4, 21, 8, 22),
				equalTo(service.findTodosById(1).getDeadline()));
	}
	
}
