package com.example.forum_4_stupid.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.forum_4_stupid.LoggerClass;
import com.example.forum_4_stupid.dto.EmailRequest;
import com.example.forum_4_stupid.exceptions.EmailAlreadyExistsException;
import com.example.forum_4_stupid.exceptions.EmailLimitHasReachedException;
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
		try {
			List<Email> emailCount = emailRepository.findByUser_Username(emailRequest.getUsername());
			if (!(emailCount.size() == 5)) {
				var email = new Email();
				email.setEmail(emailRequest.getEmail());
				email.setUser(usersRepository.findByUsername(emailRequest.getUsername()).get());
				emailRepository.save(email);				
			} 
			
			throw new EmailLimitHasReachedException("There can only be 5 emails per user"); 
		} catch (DataIntegrityViolationException e) {
			throw new EmailAlreadyExistsException("Email Already Exists");
		}
	}
	
	@Transactional(readOnly = true)
	public List<Email> getEmailByOwnerId(Integer user_ids) {
		try {
			List<Email> email = emailRepository.findByUser_Id(user_ids);
			return email;			
		} catch (NoSuchElementException e) {
			throw new EmailNotFoundByUsernameException("No Email associated with user");
		}
	}
	
	@Transactional(readOnly = true)
	public List<Email> getAllEmailFromUserByUserId (Integer owner_id) {
		try {	
			return emailRepository.findByUser_Id(owner_id);
		} catch (NoSuchElementException e) {
			LoggerClass.getLogger(EmailService.class).log(Level.INFO, "Someone Searched for an Email that does not Exist.");
			throw new EmailNotFoundByUsernameException("Email Does Not Exist");
		}
	}
	
	@Transactional(readOnly = true)
	public List<Email> getAllEmaiLFromUserByUsername(String username) {
		try {	
			List<Email> email = emailRepository.findByUser_Username(username);
			return email;
		} catch (NoSuchElementException e) {
			LoggerClass.getLogger(EmailService.class).log(Level.INFO, "Someone Searched for an Email that does not Exist.");
			throw new EmailNotFoundByUsernameException("Email Does Not Exist");
		}
	}
	
	@Transactional(readOnly = true)
	public Email getEmailById(Integer id) {
		try {	
			Email email = emailRepository.findById(id).get();
			return email;
		} catch (NoSuchElementException e) {
			LoggerClass.getLogger(EmailService.class).log(Level.INFO, "Someone Searched for an Email that does not Exist.");
			throw new EmailNotFoundByUsernameException("Email Does Not Exist");
		}
	}	
	
	
	@Transactional
	public void deleteEmail (Email entity) {
		emailRepository.delete(entity);;
	}
	
}
