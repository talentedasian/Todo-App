package com.example.forum_4_stupid.unit.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.forum_4_stupid.dto.EmailRequest;
import com.example.forum_4_stupid.model.Email;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.EmailRepository;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.service.EmailService;

@RunWith(SpringRunner.class)
public class EmailServiceTest {

	@MockBean
	private UsersRepository usersRepo;
	@MockBean
	private EmailRepository emailRepo;
	private static EmailService emailService;
	
	@Before
	public void setUp() {
		emailService = new EmailService(usersRepo, emailRepo);
	}
	
	@org.junit.Test
	public void emailServiceShoudCallEmailRepo() {
		Optional<Users> user = Optional.of(new Users(null, "test",
				LocalDateTime.now(), "testpassword", true, null, null));
		when(usersRepo.findByUsername("test")).thenReturn(user);
		var email = new Email(null, "test@gmail.com", user.get());
		when(emailRepo.save(any(Email.class))).thenReturn(email);
		emailService.addEmail(new EmailRequest("test@gmail.com", "test"));
		verify(emailRepo, times(1)).save(Mockito.any(Email.class));
		verify(usersRepo, times(1)).findByUsername("test");
		verify(emailRepo, times(1)).findByUser_Username("test");
	}
	
	@org.junit.Test
	public void emailServiceShouldEqualToActualEmailWhenAddingEmail() {
		Optional<Users> user = Optional.of(new Users(null, "test",
				LocalDateTime.now(), "testpassword", true, null, null));
		
		when(usersRepo.findByUsername("test")).thenReturn(user);
		var email = new Email(null, "test@gmail.com", user.get());
		when(emailRepo.save(any(Email.class))).thenReturn(email);
		Email actualEmail = emailService.addEmail(new EmailRequest("test@gmail.com", "test"));
		
		assertThat(actualEmail.getUser(), equalTo(email.getUser()));
		assertThat(actualEmail.getEmail(), equalTo(email.getEmail()));
		
	}
	
	@org.junit.Test
	public void emailServiceShouldEqualToActualEmailWhenGettingEmail() {
		Optional<Users> user = Optional.of(new Users(null, "test",
				LocalDateTime.now(), "testpassword", true, null, null));
		
		when(usersRepo.findByUsername("test")).thenReturn(user);
		Optional<Email> email = Optional.of(new Email(1, "test@gmail.com", user.get()));
		when(emailRepo.findById(1)).thenReturn(email);
		Email actualEmail = emailService.getEmailById(1);
		
		assertThat(actualEmail.getUser(), equalTo(email.get().getUser()));
		assertThat(actualEmail.getEmail(), equalTo(email.get().getEmail()));
		
	}
	
	@org.junit.Test
	public void emailServiceShouldCallFindByIdRepo() {
		Optional<Users> user = Optional.of(new Users(1, "test",
				LocalDateTime.now(), "testpassword",
				true, null, null));
		Optional<Email> email = Optional.of(new Email(1, "test@gmail.com", user.get()));
		when(emailRepo.findById(1)).thenReturn(email);
		emailService.getEmailById(1);
		
		verify(emailRepo, times(1)).findById(1);
	}
	
}
