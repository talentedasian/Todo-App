package com.example.forum_4_stupid.unit.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.forum_4_stupid.controller.PhoneController;
import com.example.forum_4_stupid.dto.PhoneNumberDTO;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.PhoneNumberDtoMapper;
import com.example.forum_4_stupid.hateoas.PhoneNumberDTOAssembler;

@ExtendWith(SpringExtension.class)
public class PhoneControllerTest {

	private static PhoneNumberDTO emailDTO;
	private static PhoneController controller;
	private static EntityModel<PhoneNumberDTO> entityModel;
	
	@Mock
	private PhoneNumberDTOAssembler assembler;
	@Mock
	private PhoneNumberDtoMapper mapper;
	
	@BeforeEach
	public void setUp() {
		controller = new PhoneController(mapper, assembler);
		
		var userDTO = new UserDTO();
		userDTO.setId(1);
		userDTO.setUsername("testusername");
		userDTO.setTotalPhoneNumbers(1);
		userDTO.setTotalTodos(0);
		emailDTO = new PhoneNumberDTO();
		emailDTO.setId(1);
		emailDTO.setPhoneNumber("+639321372312");
		emailDTO.setUser(userDTO);
		
		entityModel = EntityModel.of(emailDTO);
		entityModel.add(linkTo(methodOn(PhoneController.class)
				.getPhoneNumberById(emailDTO.getId()))
			.withSelfRel());
	
		entityModel.add(linkTo(methodOn(PhoneController.class)
			.getPhoneNumberByOwnerId(emailDTO.getUser().getId()))
		.withRel("inUserPhone"));
		
	}
	
	@Test
	public void verifyPhoneNumberDTOAssemblerCalled() {
		when(mapper.getById(1)).thenReturn(emailDTO);
		when(assembler.toModel(emailDTO)).thenReturn(EntityModel.of(emailDTO));
		
		controller.getPhoneNumberById(1);
		verify(assembler).toModel(emailDTO);
	}
	
	@Test
	public void shouldReturnResponseEntityWithLinks() {
		when(mapper.getById(1)).thenReturn(emailDTO);
		when(assembler.toModel(emailDTO)).thenReturn(EntityModel.of(emailDTO));
		
		ResponseEntity<EntityModel<PhoneNumberDTO>> email = controller.getPhoneNumberById(1);
		assertThat(email.getHeaders().getContentType(), 
				equalTo(org.springframework.hateoas.MediaTypes.HAL_JSON));
		
	}
	
	@Test
	public void shouldReturnExpectedLinks() {
		when(mapper.getById(1)).thenReturn(emailDTO);
		
		when(assembler.toModel(emailDTO)).thenReturn(entityModel);
		
		ResponseEntity<EntityModel<PhoneNumberDTO>> email = controller.getPhoneNumberById(1);
		
		assertThat("/api/phone/phoneNumberById?id=1", 
				equalTo(email.getBody().getLink("self").get().getHref()));
		assertThat("/api/phone/phoneNumberByOwnerId/1", 
				equalTo(email.getBody().getLink("inUserPhone").get().getHref()));
		
	}
	
	@Test
	public void shouldReturnNestedUserDTOLinks() {
		when(mapper.getById(1)).thenReturn(emailDTO);
		
		when(assembler.toModel(emailDTO)).thenReturn(entityModel);
		
		ResponseEntity<EntityModel<PhoneNumberDTO>> email = controller.getPhoneNumberById(1);
		
		assertThat("/api/user/userById/1", 
				equalTo(email.getBody().getContent().getUser().getLink("inUserById").get().getHref()));
		assertThat("/api/user/userByUsername?username=testusername", 
				equalTo(email.getBody().getContent().getUser().getLink("inUserByUsername").get().getHref()));
	}
			
}