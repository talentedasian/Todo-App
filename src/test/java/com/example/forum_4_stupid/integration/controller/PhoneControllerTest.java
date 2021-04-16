package com.example.forum_4_stupid.integration.controller;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.forum_4_stupid.controller.PhoneController;
import com.example.forum_4_stupid.dto.PhoneNumberDTO;
import com.example.forum_4_stupid.dto.PhoneNumberRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.PhoneNumberDtoMapper;
import com.example.forum_4_stupid.hateoas.PhoneNumberDTOAssembler;
import com.example.forum_4_stupid.repository.PhoneRepository;
import com.example.forum_4_stupid.repository.UsersRepository;

@WebMvcTest(controllers = PhoneController.class)
@AutoConfigureMockMvc(print = MockMvcPrint.DEFAULT, addFilters = false, printOnlyOnFailure = false)
public class PhoneControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PhoneRepository phoneRepo;
	@MockBean
	private UsersRepository userRepo;
	@MockBean
	private PhoneNumberDTOAssembler assembler;
	@MockBean
	private PhoneNumberDtoMapper mapper;
	
	private static PhoneNumberDTO phoneNumberDTO;
	private static PhoneNumberDTO phoneNumberDTO2;
	private static EntityModel<PhoneNumberDTO> entityModel;
	private static EntityModel<PhoneNumberDTO> entityModel2;
	private static CollectionModel<EntityModel<PhoneNumberDTO>> collectionModel;
	private List<PhoneNumberDTO> listPhoneDTO;
	private List<EntityModel<PhoneNumberDTO>> listEntityModel;
	
	@BeforeEach
	public void setUp() {
		var userDTO = new UserDTO(1, "testusername", 2, 0);
		phoneNumberDTO = new PhoneNumberDTO();
		phoneNumberDTO.setId(1);
		phoneNumberDTO.setPhoneNumber("+63932121231");
		phoneNumberDTO.setUser(userDTO);
		phoneNumberDTO2 = new PhoneNumberDTO();
		phoneNumberDTO2.setId(2);
		phoneNumberDTO.setPhoneNumber("+63933123212");
		phoneNumberDTO2.setUser(userDTO);
		
		entityModel = EntityModel.of(phoneNumberDTO);
		entityModel.add(linkTo(methodOn(PhoneController.class)
				.getPhoneNumberById(entityModel.getContent().getId()))
			.withSelfRel());
	
		entityModel.add(linkTo(methodOn(PhoneController.class)
			.getPhoneNumberByOwnerId(entityModel.getContent().getUser().getId()))
		.withRel("inUserPhone"));
		entityModel2 = EntityModel.of(phoneNumberDTO2);
		entityModel2.add(linkTo(methodOn(PhoneController.class)
				.getPhoneNumberById(entityModel2.getContent().getId()))
			.withSelfRel());
	
		entityModel2.add(linkTo(methodOn(PhoneController.class)
			.getPhoneNumberByOwnerId(entityModel2.getContent().getUser().getId()))
		.withRel("inUserPhone"));
		
		listPhoneDTO = new ArrayList<>();
		listPhoneDTO.add(phoneNumberDTO);
		listPhoneDTO.add(phoneNumberDTO2);
		listEntityModel = new ArrayList<>();
		
		listEntityModel.add(entityModel);
			
		collectionModel = CollectionModel.of(listEntityModel);
		collectionModel.add(linkTo(methodOn(PhoneController.class)
				.getPhoneNumberByOwnerId(phoneNumberDTO.getUser().getId()))
			.withSelfRel().withHref("http://localhost:/api/phone/phoneNumberByOwnerId/1"));
		
	}	
	
	@Test
	public void shouldReturnHal_JsonContentTypeWhenAddingPhone() throws URISyntaxException, Exception {
		when(mapper.save(Mockito.any(PhoneNumberRequest.class))).thenReturn(phoneNumberDTO);
		
		when(assembler.toModel(phoneNumberDTO)).thenReturn(entityModel);
		
		mvc.perform(post(new URI("/api/phone/add-phoneNumber"))
			.content("{\n\"phoneNumber\": \"+6393213212\",\n\"username\": \"testusername\"\n}")
			.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaTypes.HAL_JSON));
				
	}
	
	@Test
	public void shouldReturnExpectedDtoOutputsWhenAddingPhone() throws URISyntaxException, Exception {
		when(mapper.save(Mockito.any(PhoneNumberRequest.class))).thenReturn(phoneNumberDTO);
		
		when(assembler.toModel(phoneNumberDTO)).thenReturn(entityModel);
		
		mvc.perform(post(new URI("/api/phone/add-phoneNumber"))
			.content("{\n\"phoneNumber\": \"+693213214332\",\n\"username\": \"testusername\"\n}")
			.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("id",
				equalTo(phoneNumberDTO.getId())))
		.andExpect(jsonPath("phoneNumber",
				equalTo(phoneNumberDTO.getPhoneNumber())))
		.andExpect(jsonPath("user.id",
				equalTo(phoneNumberDTO.getUser().getId())))
		.andExpect(jsonPath("user.username",
				equalTo(phoneNumberDTO.getUser().getUsername())))
		.andExpect(jsonPath("user.totalPhoneNumbers",
				equalTo(phoneNumberDTO.getUser().getTotalPhoneNumbers())))
		.andExpect(jsonPath("user.totalTodos",
				equalTo(phoneNumberDTO.getUser().getTotalTodos())));
	}
	
	@Test
	public void shouldReturnExpectedNestedUserLinkOutputsWhenAddingPhone() throws URISyntaxException, Exception {
		when(mapper.save(Mockito.any(PhoneNumberRequest.class))).thenReturn(phoneNumberDTO);
		
		when(assembler.toModel(phoneNumberDTO)).thenReturn(entityModel);
		
		mvc.perform(post(new URI("/api/phone/add-phoneNumber"))
				.content("{\n\"phoneNumber\": \"+693213214332\",\n\"username\": \"testusername\"\n}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("user._links",
				isA(Map.class)))
		.andExpect(jsonPath("user._links.inUserById.href",
				endsWith("/api/user/userById/1")))
		.andExpect(jsonPath("user._links.inUserByUsername.href",
				endsWith("/api/user/userByUsername?username=testusername")));
	}
	
	@Test
	public void shouldReturnHal_JsonContentType() throws URISyntaxException, Exception { 
		when(mapper.getById(1)).thenReturn(phoneNumberDTO);
			
		when(assembler.toModel(phoneNumberDTO)).thenReturn(entityModel);
		
		mvc.perform(get(new URI("/api/phone/phoneNumberById?id=1")))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaTypes.HAL_JSON));
				
	}
	
	@Test
	@DisplayName("Should ReturnExpectedJsonOutput When GetMappingPhoneById")
	public void shouldReturnExpectedDtoOutputs() throws URISyntaxException, Exception { 
		when(mapper.getById(1)).thenReturn(phoneNumberDTO);
		
		when(assembler.toModel(phoneNumberDTO)).thenReturn(entityModel);
		
		mvc.perform(get(new URI("/api/phone/phoneNumberById?id=1")))
		.andExpect(jsonPath("id",
				equalTo(phoneNumberDTO.getId())))
		.andExpect(jsonPath("phoneNumber", 
				equalTo(phoneNumberDTO.getPhoneNumber())))
		.andExpect(jsonPath("user.id", 
				equalTo(phoneNumberDTO.getUser().getId())))
		.andExpect(jsonPath("user.username",
				equalTo(phoneNumberDTO.getUser().getUsername())))
		.andExpect(jsonPath("user.totalPhoneNumbers",
				equalTo(phoneNumberDTO.getUser().getTotalPhoneNumbers())))
		.andExpect(jsonPath("user.totalTodos",
				equalTo(phoneNumberDTO.getUser().getTotalTodos())));
	}
	
	@Test
	@DisplayName("Should ReturnExpectedLinkOutput When GetMappingPhoneById")
	public void shouldExpectedLinkOutputs() throws URISyntaxException, Exception {
		when(mapper.getById(1)).thenReturn(phoneNumberDTO);
		
		when(assembler.toModel(phoneNumberDTO)).thenReturn(entityModel);
		
		mvc.perform(get(new URI("/api/phone/phoneNumberById?id=1")))
		.andExpect(jsonPath("_links.self.href", 
				endsWith("/api/phone/phoneNumberById?id=1")));
	}
	
	@Test
	@DisplayName("Should ReturnExpectedNestedUserLinkOutput When GetMappingPhoneById")
	public void shouldExpectedNestedUserLinkOutputs() throws URISyntaxException, Exception {
		when(mapper.getById(1)).thenReturn(phoneNumberDTO);
		
		when(assembler.toModel(phoneNumberDTO)).thenReturn(entityModel);
		
		mvc.perform(get(new URI("/api/phone/phoneNumberById"))
				.param("id", "1"))
		.andExpect(jsonPath("user._links.inUserById.href", 
				endsWith("/api/user/userById/1")))
		.andExpect(jsonPath("user._links.inUserByUsername.href", 
				endsWith("/api/user/userByUsername?username=testusername")));
	}
	
	@Test
	@DisplayName("Should ReturnExpectedDtoOutputCollection When GetMappingPhoneByOwnerId")
	public void shouldExpectedDtoOutputsByCollection() throws URISyntaxException, Exception {
		when(mapper.getAllPhoneNumbersByUsersId(1)).thenReturn(listPhoneDTO);
		
		when(assembler.toCollectionModel(listPhoneDTO)).thenReturn(collectionModel);
		
		mvc.perform(get(new URI("/api/phone/phoneNumberByOwnerId/1")))
		.andExpect(jsonPath("_embedded.phoneNumberDTOList[0].id", 
				equalTo(phoneNumberDTO.getId())))
		.andExpect(jsonPath("_embedded.phoneNumberDTOList[0].phoneNumber", 
				equalTo(phoneNumberDTO.getPhoneNumber())))
		.andExpect(jsonPath("_embedded.phoneNumberDTOList[0].user.id", 
				equalTo(phoneNumberDTO.getUser().getId())))
		.andExpect(jsonPath("_embedded.phoneNumberDTOList[0].user.username", 
				equalTo(phoneNumberDTO.getUser().getUsername())))
		.andExpect(jsonPath("_embedded.phoneNumberDTOList[0].user.totalPhoneNumbers", 
				equalTo(phoneNumberDTO.getUser().getTotalPhoneNumbers())))
		.andExpect(jsonPath("_embedded.phoneNumberDTOList[0].user.totalTodos", 
				equalTo(phoneNumberDTO.getUser().getTotalTodos())));
	}
	
	@Test
	@DisplayName("Should ReturnExpectedLinkCollection When GetMappingPhoneByOwnerId")
	public void shouldExpectedLinkCollection() throws URISyntaxException, Exception {
		when(mapper.getAllPhoneNumbersByUsersId(1)).thenReturn(listPhoneDTO);
		
		when(assembler.toCollectionModel(listPhoneDTO)).thenReturn(collectionModel);
		
		mvc.perform(get(new URI("/api/phone/phoneNumberByOwnerId/1")))
		.andExpect(jsonPath("_embedded.phoneNumberDTOList[0]._links.self.href", 
				endsWith("/api/phone/phoneNumberById?id=1")))
		.andExpect(jsonPath("_embedded.phoneNumberDTOList[0]._links.inUserPhone.href", 
				endsWith("/api/phone/phoneNumberByOwnerId/1")))
		.andExpect(jsonPath("_links.self.href", 
				endsWith("/api/phone/phoneNumberByOwnerId/1")));
	}
	
	@Test
	@DisplayName("Should ReturnExpectedNestedUserLinkCollection When GetMappingPhoneByOwnerId")
	public void shouldExpectedNestedUserLinkCollection() throws URISyntaxException, Exception {
		when(mapper.getAllPhoneNumbersByUsersId(1)).thenReturn(listPhoneDTO);
		
		when(assembler.toCollectionModel(listPhoneDTO)).thenReturn(collectionModel);
		
		mvc.perform(get(new URI("/api/phone/phoneNumberByOwnerId/1")))
		.andExpect(jsonPath("_embedded.phoneNumberDTOList[0].user._links.inUserById.href", 
				endsWith("/api/user/userById/1")))
		.andExpect(jsonPath("_embedded.phoneNumberDTOList[0].user._links.inUserByUsername.href", 
				endsWith("/api/user/userByUsername?username=testusername")));
	}
	
}
