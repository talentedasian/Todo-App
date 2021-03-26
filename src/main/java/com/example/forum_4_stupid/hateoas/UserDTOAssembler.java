package com.example.forum_4_stupid.hateoas;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import com.example.forum_4_stupid.controller.UserController;
import com.example.forum_4_stupid.dto.UserDTO;

@Component
public class UserDTOAssembler implements SimpleRepresentationModelAssembler<UserDTO>{

	@Override
	public void addLinks(EntityModel<UserDTO> resource) {
		resource.add(linkTo(methodOn(UserController.class)
				.getUserInformationById(resource.getContent().getId()))
				.withRel("userById"));
		
		resource.add(linkTo(methodOn(UserController.class)
				.getUserInformationByUsername(resource.getContent().getUsername()))
				.withRel("userByUsername"));
	}

	@Override
	public void addLinks(CollectionModel<EntityModel<UserDTO>> resources) {
		// To be implemented if someone decides to do bulk user fetching(admin only).
	}

}
