package com.example.forum_4_stupid.unit.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.forum_4_stupid.exceptions.AccountDoesNotExistException;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.service.UserService;

@ExtendWith(value = SpringExtension.class)
public class UserServiceTest {

	@MockBean
	private UsersRepository repo;
	private UserService userService;	
	
	@BeforeEach
	public void setUp() {
		userService = new UserService(repo);
	}
	
	@org.junit.jupiter.api.Test
	public void shouldCallFindByIdFromUserRepo() {
		Optional<Users> user = Optional.of(new Users(1,
				"test", LocalDateTime.now(), 
				"testpassword", true,
				null, null));
		
		when(repo.findById(1)).thenReturn(user);
		userService.findUserById(1);
		verify(repo, times(1)).findById(1);
	}
	
	@org.junit.jupiter.api.Test
	public void shouldThrowAccountDoesNotExistException() {
		Optional<Users> user = Optional.of(new Users(1,
				"test", LocalDateTime.now(), 
				"testpassword", true,
				null, null));
		
		when(repo.findById(1)).thenReturn(user);
		assertThrows(AccountDoesNotExistException.class,
				() -> userService.findUserById(2));
		verify(repo, times(1)).findById(2);
	}
	
}
