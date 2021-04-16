package com.example.forum_4_stupid.utility;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.example.forum_4_stupid.controller.UserController;
import com.example.forum_4_stupid.dto.PhoneNumberDTO;
import com.example.forum_4_stupid.dto.TodoDTO;

public class NestedDTOAssembler {

	public static void addUserFromPhoneNumberNestedEntityLink(EntityModel<PhoneNumberDTO> phoneNumber) {
		phoneNumber.getContent().getUser().add(WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(UserController.class)
				.getUserInformationById(phoneNumber.getContent().getUser().getId()))
				.withRel("inUserById"));
		
		phoneNumber.getContent().getUser().add(WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(UserController.class)
				.getUserInformationByUsername(phoneNumber.getContent().getUser().getUsername()))
				.withRel("inUserByUsername"));
	}
	
	public static void addUserFromPhoneNumberNestedEntityLink(CollectionModel<EntityModel<PhoneNumberDTO>> phoneNumber) {
		for (EntityModel<PhoneNumberDTO> entityModels : phoneNumber) {
			entityModels.getContent().getUser().add(WebMvcLinkBuilder.linkTo(
					WebMvcLinkBuilder.methodOn(UserController.class)
					.getUserInformationById(entityModels.getContent().getUser().getId()))
					.withRel("inUserById"));
			
			entityModels.getContent().getUser().add(WebMvcLinkBuilder.linkTo(
					WebMvcLinkBuilder.methodOn(UserController.class)
					.getUserInformationByUsername(entityModels.getContent().getUser().getUsername()))
					.withRel("inUserByUsername"));
		}
	}
	
	public static void addUserFromTodoNestedEntityLink(EntityModel<TodoDTO> todo) {
		todo.getContent().getUser().add(WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(UserController.class)
				.getUserInformationById(todo.getContent().getUser().getId()))
				.withRel("inUserById"));
		
		todo.getContent().getUser().add(WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(UserController.class)
				.getUserInformationByUsername(todo.getContent().getUser().getUsername()))
				.withRel("inUserByUsername"));
	}
	
	public static void addUserFromTodoNestedEntityLink(CollectionModel<EntityModel<TodoDTO>> todo) {
		TodoDTO entityModel = null;
		for (EntityModel<TodoDTO> entityModels : todo) {
			entityModel = entityModels.getContent();
		}
		
		entityModel.getUser().add(WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(UserController.class)
				.getUserInformationById(entityModel.getUser().getId()))
				.withRel("inUserById"));
		
		entityModel.getUser().add(WebMvcLinkBuilder.linkTo(
				WebMvcLinkBuilder.methodOn(UserController.class)
				.getUserInformationByUsername(entityModel.getUser().getUsername()))
				.withRel("inUserByUsername"));
	}
	
}
