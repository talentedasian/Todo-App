package com.example.forum_4_stupid.unit.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.service.AuthService;
import com.example.forum_4_stupid.service.UserService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AuthService.class, UserService.class})
public class UserServiceTest {

	@MockBean
	private UsersRepository repo;
	@MockBean
	private AuthenticationManager manager;
	@MockBean
	private PasswordEncoder encoder;
	private UserService userService;
	
	@Before
	public void setUp() {
		 userService = new UserService(repo);
	}
	
	@Test
	public void shouldCallFindByIdFromUserRepo() {
		AuthService authService = new AuthService(repo, encoder, manager);
		authService.signup(new RegisterRequest("test", "testpassword"));
		when(userService.findUserById(1)).thenReturn(new Users(1,
				"test", LocalDateTime.now(), 
				"testpassword", true,
				null, null));
		userService.findUserById(1);
		verify(repo, times(1)).findById(1);
	}
}
