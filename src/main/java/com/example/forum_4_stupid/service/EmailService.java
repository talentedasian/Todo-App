package com.example.forum_4_stupid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.forum_4_stupid.dto.EmailRequest;
import com.example.forum_4_stupid.model.Email;
import com.example.forum_4_stupid.repository.EmailRepository;
import com.example.forum_4_stupid.repository.UsersRepository;

@Service
@EnableTransactionManagement
public class EmailService {

	private final UsersRepository usersRepository;
	private final EmailRepository emailRepository;
	
	@Autowired
	public EmailService (UsersRepository usersRepository, EmailRepository emailRepository) {
		this.usersRepository = usersRepository;
		this.emailRepository = emailRepository;
	}

	@Transactional
	public void addEmail (EmailRequest emailRequest) {
		var email = new Email();
		System.out.println(Thread.currentThread())
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
		email.setEmail(emailRequest.getEmail());
		email.setUser(usersRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get());
		emailRepository.save(email);
	}
}
