package com.example.forum_4_stupid.hateoas;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.forum_4_stupid.controller.TodoController;
import com.example.forum_4_stupid.dto.TodoDTO;

@Component
public class TodoDTOAssembler implements SimpleRepresentationModelAssembler<TodoDTO>{

	@Override
	public void addLinks(EntityModel<TodoDTO> resource) {
		resource.add(linkTo(methodOn(TodoController.class)
				.getTodoById(resource.getContent().getId()))
				.withSelfRel());
		
		resource.add(linkTo(methodOn(TodoController.class)
				.getTodoByUserId(resource.getContent().getUser().getId()))
				.withSelfRel());
	}

	@Override
	public void addLinks(CollectionModel<EntityModel<TodoDTO>> resources) {
		
		
	}

}
