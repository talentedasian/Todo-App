package com.example.forum_4_stupid.service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.forum_4_stupid.dto.RegisterRequest;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.model.VerificationToken;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.example.forum_4_stupid.repository.VerificationTokenRepository;

@Service
public class AuthService {
	
	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
	public AuthService (UsersRepository usersRepository, PasswordEncoder passwordEncoder, 
			VerificationTokenRepository verifacationRepository) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
		this.verificationTokenRepository = verifacationRepository;
	}
	
	@Transactional
	public void signup (RegisterRequest registerRequest) {
		var user = new Users();
		user.setUsername(registerRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setDateCreated(Instant.now());
		user.setEnabled(false);
		usersRepository.save(user);

	}
	
	public void login () {
		
	}
	
	@Transactional
	private String generateVerificationToken () {
		var verificationToken = new VerificationToken();
		verificationToken.setExpiryDate(Date.from(Instant.now().plusSeconds(172800L)));
		verificationToken.setToken(UUID.randomUUID().toString());
		verificationTokenRepository.save(verificationToken);
		
		return verificationToken.getToken();
		
	}
	
	
	public void verifyUser () {
		
	}
	
	
	
}
