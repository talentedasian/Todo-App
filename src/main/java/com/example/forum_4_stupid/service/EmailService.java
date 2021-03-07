package com.example.forum_4_stupid.service;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.forum_4_stupid.LoggerClass;
import com.example.forum_4_stupid.dto.EmailRequest;
import com.example.forum_4_stupid.exceptions.EmailNotFoundByUsernameException;
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
		email.setEmail(emailRequest.getEmail());
		email.setUser(usersRepository.findByUsername(emailRequest.getUsername()).get());
		emailRepository.save(email);
	}
	
	public List<Email> getEmail (String owner_id) {
		try {	
			return emailRepository.findByUser_Id(Integer.parseInt(owner_id));
		} catch (EmailNotFoundByUsernameException e) {
			Logger logger = LoggerClass.getLogger(UserService.class);
			logger.log(Level.INFO, "Someone Searched for an Email that does not Exist.");
			throw new EmailNotFoundByUsernameException("Email Does Not Exist", e);
		}
	}
}
