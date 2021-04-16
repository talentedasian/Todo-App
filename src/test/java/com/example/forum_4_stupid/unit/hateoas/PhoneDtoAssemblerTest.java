package com.example.forum_4_stupid.unit.hateoas;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import com.example.forum_4_stupid.dto.PhoneNumberDTO;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.hateoas.PhoneNumberDTOAssembler;
import com.example.forum_4_stupid.utility.NestedDTOAssembler;
import com.fasterxml.jackson.core.JsonProcessingException;

public class PhoneDtoAssemblerTest {

	private static PhoneNumberDTO phoneNumberDTO;
	
	private static PhoneNumberDTOAssembler assembler;
	
	@BeforeEach
	public void setUp() {
		phoneNumberDTO = new PhoneNumberDTO();
		phoneNumberDTO.setId(2);
		phoneNumberDTO.setPhoneNumber("+6393212221321");
		phoneNumberDTO.setUser(new UserDTO(1, "test", 1, 0));
		
		assembler = new PhoneNumberDTOAssembler();
	}
	
	
	@Test
	public void shouldReturnEntityModelExpectedLinks() throws JsonProcessingException {
		EntityModel<PhoneNumberDTO> entityModel = assembler.toModel(phoneNumberDTO);
		assertThat("/api/phone/phoneNumberById?id=2", 
				equalTo(entityModel.getLink("self").get().getHref()));
		
	}	
	
	@Test
	public void shouldReturnCollcetionModelExpectedLinks() {
		List<PhoneNumberDTO> listEmailDTO = new ArrayList<>();
		listEmailDTO.add(phoneNumberDTO);
		CollectionModel<EntityModel<PhoneNumberDTO>> collectionModel = assembler.toCollectionModel(listEmailDTO);
		NestedDTOAssembler.addUserFromPhoneNumberNestedEntityLink(collectionModel);
		
		assertThat("/api/phone/phoneNumberByOwnerId/1", 
				equalTo(collectionModel.getLink("self").get().getHref()));
		
		for (EntityModel<PhoneNumberDTO> entityModel : collectionModel) {
			assertThat("/api/user/userById/1", 
				equalTo(entityModel.getContent().getUser().getLink("inUserById").get().getHref()));
			assertThat("/api/user/userByUsername?username=test", 
				equalTo(entityModel.getContent().getUser().getLink("inUserByUsername").get().getHref()));			
		}
		
	}
	
}
