package com.example.forum_4_stupid.unit.service;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.forum_4_stupid.dto.EmailRequest;
import com.example.forum_4_stupid.repository.EmailRepository;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.service.EmailService;

public class EmailServiceTest {

	private static EmailService emailService;
	@MockBean
	private UsersRepository usersRepo;
	@MockBean
	private EmailRepository emailRepo;
	
	@Before
	public void setUp() {
		emailService = new EmailService(usersRepo, emailRepo);
	}
	
	@Test
	public void emailServiceShoudCallEmailRepo() {
		emailService.addEmail(new EmailRequest("test@gmail.com", "test"));
	}
}
