package com.example.forum_4_stupid.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.forum_4_stupid.LoggerClass;
import com.example.forum_4_stupid.exceptions.EmailNotFoundByUsernameException;
import com.example.forum_4_stupid.model.Email;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.EmailRepository;
import com.example.forum_4_stupid.repository.UsersRepository;

@Service
@EnableTransactionManagement
public class UserService {

	
	private final UsersRepository usersRepository;
	
	@Autowired
	public UserService (UsersRepository usersRepository, EmailRepository emailRepository) {
		this.usersRepository = usersRepository;
		this.emailRepository = emailRepository;
	}
	
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public Optional<Users> getUser (String username) {
		
		Optional<Users> user = Optional.ofNullable(usersRepository.findByUsername(username).orElseThrow());
		
		return user;
	}
	
	public Optional<Users> findUserById (Integer user_id) {
		Optional<Users> user = Optional.ofNullable(usersRepository.findById(user_id).orElseThrow());
		
		return user;
	}
	
}
