package com.example.forum_4_stupid.utility;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.example.forum_4_stupid.controller.UserController;
import com.example.forum_4_stupid.dto.EmailDTO;

public class NestedDTOAssembler {

	public EntityModel<EmailDTO> addUserNestedEntityLink(EntityModel<EmailDTO> email) {
		email.getContent().getUser().add(WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(UserController.class)
				.getUserInformationById(email.getContent().getUser().getId()))
				.withRel("inUserById"));
		
		email.getContent().getUser().add(WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(UserController.class)
				.getUserInformationByUsername(email.getContent().getUser().getUsername()))
				.withRel("inUserByUsername"));

		return email;
	}
	
}
