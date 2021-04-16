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
import com.example.forum_4_stupid.dto.PhoneNumberRequest;
import com.example.forum_4_stupid.exceptions.AccountDoesNotExistException;
import com.example.forum_4_stupid.exceptions.PhoneNumberAlreadyExistsException;
import com.example.forum_4_stupid.exceptions.PhoneNumberLimitHasReachedException;
import com.example.forum_4_stupid.exceptions.PhoneNumberNotFoundByUsernameException;
import com.example.forum_4_stupid.model.PhoneNumber;
import com.example.forum_4_stupid.repository.PhoneRepository;
import com.example.forum_4_stupid.repository.UsersRepository;

@Service
@EnableTransactionManagement
public class PhoneService {

	private final UsersRepository usersRepository;
	private final PhoneRepository phoneRepository;
	
	@Autowired
	public PhoneService (UsersRepository usersRepository, PhoneRepository phoneRepository) {
		this.usersRepository = usersRepository;
		this.phoneRepository = phoneRepository;
	}

	@Transactional
	public PhoneNumber addPhoneNumber (PhoneNumberRequest phoneRequest) {
		try {
			List<PhoneNumber> emailCount = phoneRepository.findByUser_Username(phoneRequest.getUsername());
			if (emailCount.size() == 5) {
				throw new PhoneNumberLimitHasReachedException("There can only be 5 emails per user"); 
			}
			
			var phoneNumber = new PhoneNumber();
			phoneNumber.setPhoneNumber(phoneRequest.getPhoneNumber());
			phoneNumber.setUser(usersRepository.findByUsername(phoneRequest.getUsername())
					.orElseThrow(() -> new AccountDoesNotExistException("Account does not exist on the given Username")));
			phoneRepository.save(phoneNumber);
			
			return phoneNumber;
		} catch (DataIntegrityViolationException e) {
			throw new PhoneNumberAlreadyExistsException("Phone Number Already Exists");
		}
	}
	
	@Transactional(readOnly = true)
	public List<PhoneNumber> getPhoneNumberByOwnerId(Integer user_ids) {
		try {   
			List<PhoneNumber> phoneNumber = phoneRepository.findByUser_Id(user_ids);
			return phoneNumber;			
		} catch (NoSuchElementException e) {
			throw new PhoneNumberNotFoundByUsernameException("No PhoneNumber associated with user");
		}
	}
	
	@Transactional(readOnly = true)
	public List<PhoneNumber> getAllPhoneNumberFromUserByUserId (Integer owner_id) {
		try {	
			return phoneRepository.findByUser_Id(owner_id);
		} catch (NoSuchElementException e) {
			LoggerClass.getLogger(PhoneService.class).log(Level.INFO, "Someone Searched for a Phone Number that does not Exist.");
			throw new PhoneNumberNotFoundByUsernameException("Phone Number Does Not Exist");
		}
	}
	
	@Transactional(readOnly = true)
	public List<PhoneNumber> getAllEmaiLFromUserByUsername(String username) {
		try {	
			List<PhoneNumber> email = phoneRepository.findByUser_Username(username);
			return email;
		} catch (NoSuchElementException e) {
			LoggerClass.getLogger(PhoneService.class).log(Level.INFO, "Someone Searched for aa Phone Number that does not Exist.");
			throw new PhoneNumberNotFoundByUsernameException("PhoneNumber Does Not Exist");
		}
	}
	
	@Transactional(readOnly = true)
	public PhoneNumber getPhoneNumberById(Integer id) {
		try {	
			PhoneNumber email = phoneRepository.findById(id).get();
			return email;
		} catch (NoSuchElementException e) {
			LoggerClass.getLogger(PhoneService.class).log(Level.INFO, "Someone Searched for a Phone Number that does not Exist.");
			throw new PhoneNumberNotFoundByUsernameException("Phone Number Does Not Exist");
		}
	}	
	
	
	@Transactional
	public void deletePhoneNumber (PhoneNumber entity) {
		phoneRepository.delete(entity);;
	}
	
}
