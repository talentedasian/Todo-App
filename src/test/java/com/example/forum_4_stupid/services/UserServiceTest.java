package com.example.forum_4_stupid.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.service.AuthService;

@RunWith(SpringRunner.class)
public class UserServiceTest {
	
	@MockBean
	private UsersRepository repo;
	@MockBean
	private PasswordEncoder encoder;
	@MockBean
	private AuthenticationManager manager;
	
	@Test
	public void verifyUserServiceSave() {		
		AuthService service = new AuthService(repo, encoder, manager);
		service.signup(new RegisterRequest("test", "testpassword"));
		
		Mockito.verify(repo, Mockito.times(1)).save(Mockito.any(Users.class));
	}
	
	
	
}
