package com.example.forum_4_stupid.unit.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.forum_4_stupid.dto.LoginRequest;
import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.exceptions.BadRequestException;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.service.AuthService;

@RunWith(SpringRunner.class)
public class AuthServiceTest {
	
	@MockBean
	private UsersRepository repo;
	@MockBean
	private PasswordEncoder encoder;
	@MockBean
	private AuthenticationManager manager;
	
	@Test
	public void verifyUserSignUpServiceSave() {		
		AuthService service = new AuthService(repo, encoder, manager);
		service.signup(new RegisterRequest("test", "t"));
		verify(repo, Mockito.times(1)).save(Mockito.any(Users.class));
	}
	
	@Test
	public void verifyUserLoginAuthManagerAuthenticate() {		
		AuthService service = new AuthService(repo, encoder, manager);
		service.login(new LoginRequest("test", "testpassword"));
		
		verify(manager, times(1)).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));

	}
	
	@Test
	public void verifyUserLoginReturnUsernamePasswordAuthToken() {		
		AuthService service = new AuthService(repo, encoder, manager);
		UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken("test", "testpassword");
		when(service.login(new LoginRequest("test", "testpassword"))).thenReturn(upToken);
		Authentication usernamePasswordAuthToken = service.login(new LoginRequest("test", "testpassword"));
		verify(manager, times(1)).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
		
		assertThat(usernamePasswordAuthToken, 
				equalTo(new UsernamePasswordAuthenticationToken("test", "testpassword")));

	}	
	
	@Test
	public void shouldReturnBadRequestException() {		
		AuthService service = new AuthService(repo, encoder, manager);
		assertThrows(BadRequestException.class, () -> service.signup(new RegisterRequest("test", "shortUS")));
	}

}
