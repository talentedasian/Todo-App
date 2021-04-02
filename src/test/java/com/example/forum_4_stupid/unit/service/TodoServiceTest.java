package com.example.forum_4_stupid.unit.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.forum_4_stupid.dto.TodoRequest;
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
		todoRequest.setContent("test content");
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
				"test content", 
				"test title",
				LocalDateTime.of(2021, 4, 21, 8, 22),
				LocalDateTime.now(), 
				user));
		
		when(userRepo.findByUsername("test")).thenReturn(Optional.of(user));
		when(todosRepo.save(todos.get())).thenReturn(todos.get());
		service.addTodos(todoRequest);
		verify(todosRepo, times(1)).save(any(Todos.class));
		verify(userRepo, times(1)).findByUsername("test");
	}
	
}
