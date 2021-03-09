package com.example.forum_4_stupid.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.example.forum_4_stupid.exceptions.AccountDoesNotExistException;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.UsersRepository;

@Service
@EnableTransactionManagement
public class UserService{

	
	private final UsersRepository usersRepository;
	
	@Autowired
	public UserService (UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}
	
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public Users findUserByUsername (String username) {
		try {
			Users user = usersRepository.findByUsername(username).get();
			return user;
		} catch (NoSuchElementException e) {
			throw new AccountDoesNotExistException("Account Does Not Exist");
		}
	}
	
	@Transactional(readOnly = true)
	public Users findUserById (Integer user_id) {
		try {
			Users user = usersRepository.findById(user_id).get();
			return user;
		} catch (NoSuchElementException e) {
			throw new AccountDoesNotExistException("Account Does Not Exist");
		}
	}

}
