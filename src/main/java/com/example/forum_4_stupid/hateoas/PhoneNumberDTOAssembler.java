package com.example.forum_4_stupid.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.controller.PhoneController;
import com.example.forum_4_stupid.dto.PhoneNumberDTO;

@Component
public class PhoneNumberDTOAssembler implements SimpleRepresentationModelAssembler<PhoneNumberDTO>{

	@Override
	public void addLinks(EntityModel<PhoneNumberDTO> resource) {
		resource.add(linkTo(methodOn(PhoneController.class)
					.getPhoneNumberById(resource.getContent().getId()))
				.withSelfRel());
		
		resource.add(linkTo(methodOn(PhoneController.class)
				.getPhoneNumberByOwnerId(resource.getContent().getUser().getId()))
			.withRel("inUserPhone"));
	}

	@Override
	public void addLinks(CollectionModel<EntityModel<PhoneNumberDTO>> resources) {
		PhoneNumberDTO entityModel = null;
		for (EntityModel<PhoneNumberDTO> entityModels : resources) {
			entityModel = entityModels.getContent();
		}
		resources.add(linkTo(methodOn(PhoneController.class)
					.getPhoneNumberByOwnerId(entityModel.getUser().getId()))
				.withSelfRel());
	}

}
