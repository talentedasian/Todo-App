package com.example.forum_4_stupid.controller;

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
import com.example.forum_4_stupid.model.Email;
import com.example.forum_4_stupid.service.EmailService;
import com.example.forum_4_stupid.service.UserService;

@RestController
@RequestMapping
public class EmailController {

	private final EmailService emailService; 
	private final UserService userService;
	
	
	public EmailController(EmailService emailService, UserService userService) {
		this.emailService = emailService;
		this.userService = userService;
	}

	@PostMapping("/add-email")
	public ResponseEntity<EmailDTO> addEmail (@ModelAttribute EmailRequest emailRequest) {
		EmailDTO emailDTO = new EmailDTO();
		emailDTO.setEmail(emailRequest.getEmail());
		emailService.addEmail(emailRequest);
		
		return new ResponseEntity<EmailDTO>(emailDTO, HttpStatus.OK);
	}
	
	@GetMapping("/email/{owner_id}")
	public ResponseEntity<EmailDTO> getEmail(@PathVariable String owner_id) {
		Email email = userService.getEmail(owner_id).get(0);
		
		var emailDtoMapper = new EmailDtoMapper();
		EmailDTO emailResponse = emailDtoMapper.returnEmail(email);
		
		return new ResponseEntity<EmailDTO>(emailResponse, HttpStatus.OK);
	}
	
}
