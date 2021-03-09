package com.example.forum_4_stupid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.dto.EmailRequest;
import com.example.forum_4_stupid.dtoMapper.EmailDtoMapper;
import com.example.forum_4_stupid.dtoMapper.interfaces.DTOMapper;
import com.example.forum_4_stupid.dtoMapper.interfaces.PersistentDTOMapper;
import com.example.forum_4_stupid.model.Email;
import com.example.forum_4_stupid.service.EmailService;
import com.example.forum_4_stupid.service.UserService;

@RestController
@RequestMapping("/api/email")
public class EmailController {

	private final EmailService emailService; 
	private final EmailDtoMapper emailDtoMapper; 
	
	@Autowired
	public EmailController(EmailService emailService, EmailDtoMapper emailDtoMapper) {
		this.emailService = emailService;
		this.emailDtoMapper = emailDtoMapper;
	}

	@PostMapping("/add-email")
	public void addEmail (@ModelAttribute EmailRequest emailRequest) {
		emailDtoMapper.save(emailRequest);
	}
	
	@GetMapping("/{owner_id}")
	public ResponseEntity<EmailDTO> getEmail(@PathVariable String owner_id) {
		Email email = emailService.getEmailByOwnerId(Integer.parseInt(owner_id));
		EmailDTO response = emailDtoMapper.returnEntity(email);
		
		return new ResponseEntity<EmailDTO>(response, HttpStatus.OK);
	}
	
}
