package com.example.forum_4_stupid.unit.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.forum_4_stupid.exceptions.AccountDoesNotExistException;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.service.UserService;
@RunWith(SpringRunner.class)
public class UserServiceTest {

	@MockBean
	private UsersRepository repo;
	private UserService userService;	
	
	@Before
	public void setUp() {
		userService = new UserService(repo);
	}
	
	@Test
	public void shouldCallFindByIdFromUserRepo() {
		Optional<Users> user = Optional.of(new Users(1,
				"test", LocalDateTime.now(), 
				"testpassword", true,
				null, null));
		
		when(repo.findById(1)).thenReturn(user);
		userService.findUserById(1);
		verify(repo, times(1)).findById(1);
	}
	
	@Test(expected = AccountDoesNotExistException.class)
	public void shouldThrowAccountDoesNotExistException() {
		Optional<Users> user = Optional.of(new Users(1,
				"test", LocalDateTime.now(), 
				"testpassword", true,
				null, null));
		
		userService = new UserService(repo);
		when(repo.findById(1)).thenReturn(user);
		userService.findUserById(2);
		verify(repo, times(1)).findById(2);
	}
	
}
