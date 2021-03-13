package com.example.forum_4_stupid.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.dto.EmailRequest;
import com.example.forum_4_stupid.dtoMapper.EmailDtoMapper;
import com.example.forum_4_stupid.hateoas.EmailDTOAssembler;
import com.example.forum_4_stupid.service.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {

	private final EmailDtoMapper emailDtoMapper;
	private final EmailDTOAssembler emailAssembler;
	
	public EmailController(EmailDtoMapper emailDtoMapper, EmailDTOAssembler emailAssembler) {
		this.emailDtoMapper = emailDtoMapper;
		this.emailAssembler = emailAssembler;
	}

	@PostMapping("/add-email")
	public void addEmail (@ModelAttribute EmailRequest emailRequest) {
		emailDtoMapper.save(emailRequest);
	}
	
	@GetMapping("/id")
	public ResponseEntity<EntityModel<EmailDTO>> getEmailById(@RequestParam Integer id) {
		EmailDTO email = emailDtoMapper.getById(id);
		EntityModel<EmailDTO> assembler = emailAssembler.toModel(email);
		
		
		return new ResponseEntity<EntityModel<EmailDTO>>(assembler, HttpStatus.OK);
	}
	
	@GetMapping("/{owner_id}")
	public ResponseEntity<CollectionModel<EmailDTO>> getEmailByOwnerId(@PathVariable Integer owner_id) {
		List<EmailDTO> email = emailDtoMapper.getAllEmailByUsersId(owner_id);
		
			for (EmailDTO emailDTO : email) {
				emailDTO.add(linkTo(methodOn(EmailController.class)
						.getEmailById(emailDTO.getId())).withSelfRel());
				emailDTO.getUser().add(linkTo(methodOn(UserController.class)
						.getUserInformationById(String.valueOf(owner_id))).withSelfRel());
			}
			
		Link allEmailByOwnerId = linkTo(methodOn(EmailController.class)
				.getEmailByOwnerId(owner_id)).withSelfRel();
		
		return ResponseEntity.ok(CollectionModel.of(email, allEmailByOwnerId));

	}
	
}
