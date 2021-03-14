package com.example.forum_4_stupid.hateoas;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.controller.EmailController;
import com.example.forum_4_stupid.dto.EmailDTO;

@Component
public class EmailDTOAssembler implements SimpleRepresentationModelAssembler<EmailDTO>{

	@Override
	public void addLinks(EntityModel<EmailDTO> resource) {
		resource.add(linkTo(methodOn(EmailController.class)
					.getEmailById(resource.getContent().getId()))
				.withSelfRel());
		resource.add(linkTo(methodOn(EmailController.class)
				.getEmailByOwnerId(resource.getContent().getUser().getId()))
			.withRel("inUserEmail"));
		
	}

	@Override
	public void addLinks(CollectionModel<EntityModel<EmailDTO>> resources) {
		EmailDTO entityModel = null;
		for (EntityModel<EmailDTO> entityModels : resources) {
			entityModel = entityModels.getContent();
		}
		resources.add(linkTo(methodOn(EmailController.class)
					.getEmailByOwnerId(entityModel.getUser().getId()))
				.withSelfRel());

		System.out.println(entityModel.getEmail());
	}

}
