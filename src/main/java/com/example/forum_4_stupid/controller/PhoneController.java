package com.example.forum_4_stupid.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.PhoneNumberDTO;
import com.example.forum_4_stupid.dto.PhoneNumberRequest;
import com.example.forum_4_stupid.dtoMapper.PhoneNumberDtoMapper;
import com.example.forum_4_stupid.hateoas.PhoneNumberDTOAssembler;
import com.example.forum_4_stupid.utility.NestedDTOAssembler;

@RestController
@RequestMapping("/api/phone")
public class PhoneController {

	private final PhoneNumberDtoMapper phoneDtoMapper;
	private final PhoneNumberDTOAssembler phoneAssembler;
	
	@Autowired
	public PhoneController(PhoneNumberDtoMapper phoneDtoMapper, PhoneNumberDTOAssembler phoneAssembler) {
		this.phoneDtoMapper = phoneDtoMapper;
		this.phoneAssembler = phoneAssembler;
	}

	@PostMapping("/add-phoneNumber")
	public ResponseEntity<EntityModel<PhoneNumberDTO>> addPhoneNumber (@Valid @RequestBody PhoneNumberRequest emailRequest) {		
		PhoneNumberDTO emailDTO = phoneDtoMapper.save(emailRequest);
		EntityModel<PhoneNumberDTO> assembler = phoneAssembler.toModel(emailDTO);
		NestedDTOAssembler.addUserFromPhoneNumberNestedEntityLink(assembler);
		
		return new ResponseEntity<EntityModel<PhoneNumberDTO>>(assembler,getHeaders(),HttpStatus.CREATED);
	}
	
	@GetMapping("/phoneNumberById")
	public ResponseEntity<EntityModel<PhoneNumberDTO>> getPhoneNumberById(@RequestParam Integer id) {
		PhoneNumberDTO email = phoneDtoMapper.getById(id);
		EntityModel<PhoneNumberDTO> assembler = phoneAssembler.toModel(email);
		NestedDTOAssembler.addUserFromPhoneNumberNestedEntityLink(assembler);
		
		return new ResponseEntity<EntityModel<PhoneNumberDTO>>(assembler,getHeaders(),HttpStatus.OK);
	}
	
	@GetMapping("/phoneNumberByOwnerId/{owner_id}")
	public ResponseEntity<CollectionModel<EntityModel<PhoneNumberDTO>>> getPhoneNumberByOwnerId(@PathVariable Integer owner_id) {
		List<PhoneNumberDTO> email = phoneDtoMapper.getAllPhoneNumbersByUsersId(owner_id);
		CollectionModel<EntityModel<PhoneNumberDTO>> assembler = phoneAssembler.toCollectionModel(email);
		NestedDTOAssembler.addUserFromPhoneNumberNestedEntityLink(assembler);
		
		return new ResponseEntity<CollectionModel<EntityModel<PhoneNumberDTO>>>(assembler, getHeaders(), HttpStatus.OK);
	}
	
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(org.springframework.hateoas.MediaTypes.HAL_JSON);
		
		return headers;
	}
	
}
