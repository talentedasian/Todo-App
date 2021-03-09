package com.example.forum_4_stupid.controller;

import java.util.List;

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
import com.example.forum_4_stupid.service.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {

	private final EmailDtoMapper emailDtoMapper; 
	
	public EmailController(EmailService emailService, EmailDtoMapper emailDtoMapper) {
		this.emailDtoMapper = emailDtoMapper;
	}

	@PostMapping("/add-email")
	public void addEmail (@ModelAttribute EmailRequest emailRequest) {
		emailDtoMapper.save(emailRequest);
	}
	
	@GetMapping("/{owner_id}")
	public ResponseEntity<List<EmailDTO>> getEmail(@PathVariable String owner_id) {
		List<EmailDTO> response = emailDtoMapper.getAllEmailByUsersId(Integer.parseInt(owner_id));
		
		return new ResponseEntity<List<EmailDTO>>(response, HttpStatus.OK);
	}
	
}
