package com.example.forum_4_stupid.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
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
import com.example.forum_4_stupid.hateoas.EmailRepresentationalModel;
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
	public ResponseEntity<List<EmailRepresentationalModel>> getEmail(@PathVariable String owner_id) {
		List<EmailDTO> email = emailDtoMapper.getAllEmailByUsersId(Integer.parseInt(owner_id));
		List<EmailRepresentationalModel> emailResponse = new ArrayList<>();
		
		for (EmailDTO emailDTO : email) {
			EmailRepresentationalModel emailModel = new EmailRepresentationalModel();
			emailModel.setId(emailDTO.getId().toString());
			emailModel.setEmail(emailDTO.getEmail());
			emailModel.add(Link.of("http://localhost:8080/api/email/" + owner_id));
			
			emailResponse.add(emailModel)
;		}
		
		return new ResponseEntity<List<EmailRepresentationalModel>>(emailResponse, HttpStatus.OK);
	}
	
}
