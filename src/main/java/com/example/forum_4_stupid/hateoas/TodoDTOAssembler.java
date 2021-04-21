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
	public void addLinks(EntityModel<TodoDTO> resources) {
		resources.add(linkTo(methodOn(TodoController.class)
				.getTodoById(resources.getContent().getId()))
				.withRel("self"));
		resources.add(linkTo(methodOn(TodoController.class)
				.getTodoByUserId(resources.getContent().getUser().getId()))
				.withRel("inUserTodo"));
	}

	@Override
	public void addLinks(CollectionModel<EntityModel<TodoDTO>> resources) {
		TodoDTO todoDTO = null;
		
		for (EntityModel<TodoDTO> entityModel : resources) {
			todoDTO = entityModel.getContent();
		}
		
		resources.add(linkTo(methodOn(TodoController.class)
				.getTodoByUserId(todoDTO.getUser().getId()))
				.withSelfRel());
	}

}
