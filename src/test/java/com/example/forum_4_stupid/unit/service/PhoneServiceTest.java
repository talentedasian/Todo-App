package com.example.forum_4_stupid.unit.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.forum_4_stupid.dto.PhoneNumberRequest;
import com.example.forum_4_stupid.model.PhoneNumber;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.PhoneRepository;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.service.PhoneService;

@ExtendWith(SpringExtension.class)
public class PhoneServiceTest {

	@Mock
	private UsersRepository usersRepo;
	@Mock
	private PhoneRepository phoneRepo;
	
	private static PhoneService PhoneNumberService;
	
	@BeforeEach
	public void setUp() {
		PhoneNumberService = new PhoneService(usersRepo, phoneRepo);
	}
	
	@Test
	public void PhoneNumberServiceShoudCallPhoneRepo() {
		Optional<Users> user = Optional.of(new Users(1, "testusername", LocalDateTime.now(), "testpassword", true, 
				null, null));
		when(usersRepo.findByUsername("test")).thenReturn(user);
		var PhoneNumber = new PhoneNumber(null, "test@gmail.com", user.get());
		when(phoneRepo.save(any(PhoneNumber.class))).thenReturn(PhoneNumber);
		PhoneNumberService.addPhoneNumber(new PhoneNumberRequest("+639564462557", "test"));
		verify(phoneRepo, times(1)).save(Mockito.any(PhoneNumber.class));
		verify(usersRepo, times(1)).findByUsername("test");
		verify(phoneRepo, times(1)).findByUser_Username("test");
	}
	
	@Test
	public void PhoneNumberServiceShouldEqualToActualPhoneWhenAddingPhone() {
		Optional<Users> user = Optional.of(new Users(null, "test",
				LocalDateTime.now(), "testpassword", true, null, null));
		
		when(usersRepo.findByUsername("test")).thenReturn(user);
		var phoneNumber = new PhoneNumber(null, "+639564462557", user.get());
		when(phoneRepo.save(any(PhoneNumber.class))).thenReturn(phoneNumber);
		PhoneNumber actualPhone = PhoneNumberService.addPhoneNumber(new PhoneNumberRequest("+639564462557", "test"));
		
		assertThat(actualPhone.getUser(), equalTo(phoneNumber.getUser()));
		assertThat(actualPhone.getPhoneNumber(), equalTo(phoneNumber.getPhoneNumber()));
		
	}
	
	@Test
	public void PhoneNumberServiceShouldEqualToActualPhoneWhenGettingPhone() {
		Optional<Users> user = Optional.of(new Users(null, "test",
				LocalDateTime.now(), "testpassword", true, null, null));
		
		when(usersRepo.findByUsername("test")).thenReturn(user);
		Optional<PhoneNumber> PhoneNumber = Optional.of(new PhoneNumber(1, "test@gmail.com", user.get()));
		when(phoneRepo.findById(1)).thenReturn(PhoneNumber);
		PhoneNumber actualPhone = PhoneNumberService.getPhoneNumberById(1);
		
		assertThat(actualPhone.getUser(), equalTo(PhoneNumber.get().getUser()));
		assertThat(actualPhone.getPhoneNumber(), equalTo(PhoneNumber.get().getPhoneNumber()));
		
	}
	
	@Test
	public void PhoneNumberServiceShouldCallFindByIdRepo() {
		Optional<Users> user = Optional.of(new Users(1, "test",
				LocalDateTime.now(), "testpassword",
				true, null, null));
		Optional<PhoneNumber> PhoneNumber = Optional.of(new PhoneNumber(1, "test@gmail.com", user.get()));
		when(phoneRepo.findById(1)).thenReturn(PhoneNumber);
		PhoneNumberService.getPhoneNumberById(1);
		
		verify(phoneRepo, times(1)).findById(1);
	}
	
}
