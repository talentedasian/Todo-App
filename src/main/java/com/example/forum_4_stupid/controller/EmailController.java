package com.example.forum_4_stupid.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.dto.EmailRequest;
import com.example.forum_4_stupid.dtoMapper.EmailDtoMapper;
import com.example.forum_4_stupid.hateoas.EmailDTOAssembler;
import com.example.forum_4_stupid.utility.NestedDTOAssembler;

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
	public ResponseEntity<EntityModel<EmailDTO>> addEmail (@Valid @RequestBody EmailRequest emailRequest) {		
		EmailDTO emailDTO = emailDtoMapper.save(emailRequest);
		EntityModel<EmailDTO> assembler = emailAssembler.toModel(emailDTO);
		var utilityMethod = new NestedDTOAssembler();
		utilityMethod.addUserFromEmailNestedEntityLink(assembler);
		
		return new ResponseEntity<EntityModel<EmailDTO>>(assembler,HttpStatus.CREATED);
	}
	
	@GetMapping("/emailById")
	public ResponseEntity<EntityModel<EmailDTO>> getEmailById(@RequestParam Integer id) {
		EmailDTO email = emailDtoMapper.getById(id);
		EntityModel<EmailDTO> assembler = emailAssembler.toModel(email);
		var utilityMethod = new NestedDTOAssembler();
		utilityMethod.addUserFromEmailNestedEntityLink(assembler);
		
		return new ResponseEntity<EntityModel<EmailDTO>>(assembler, HttpStatus.OK);
	}
	
	@GetMapping("/emailByOwnerId/{owner_id}")
	public ResponseEntity<CollectionModel<EntityModel<EmailDTO>>> getEmailByOwnerId(@PathVariable Integer owner_id) {
		List<EmailDTO> email = emailDtoMapper.getAllEmailByUsersId(owner_id);
		CollectionModel<EntityModel<EmailDTO>> assembler = emailAssembler.toCollectionModel(email);
		var utilityMethod = new NestedDTOAssembler();
		utilityMethod.addUserFromEmailNestedEntityLink(assembler);
		
		return ResponseEntity.ok(emailAssembler.toCollectionModel(email));
	}
	
}
