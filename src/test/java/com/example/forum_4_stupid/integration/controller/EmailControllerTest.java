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

import com.example.forum_4_stupid.controller.EmailController;
import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.dto.EmailRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.EmailDtoMapper;
import com.example.forum_4_stupid.hateoas.EmailDTOAssembler;
import com.example.forum_4_stupid.repository.EmailRepository;
import com.example.forum_4_stupid.repository.UsersRepository;

@WebMvcTest(controllers = EmailController.class)
@AutoConfigureMockMvc(print = MockMvcPrint.DEFAULT, addFilters = false, printOnlyOnFailure = false)
public class EmailControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private EmailRepository emailRepo;
	@MockBean
	private UsersRepository userRepo;
	@MockBean
	private EmailDTOAssembler assembler;
	@MockBean
	private EmailDtoMapper mapper;
	
	private static EmailDTO emailDTO;
	private static EmailDTO emailDTO2;
	private static EntityModel<EmailDTO> entityModel;
	private static EntityModel<EmailDTO> entityModel2;
	private static CollectionModel<EntityModel<EmailDTO>> collectionModel;
	private List<EmailDTO> listEmailDTO;
	private List<EntityModel<EmailDTO>> listEntityModel;
	
	@BeforeEach
	public void setUp() {
		var userDTO = new UserDTO(1, "testusername", 2, 0);
		emailDTO = new EmailDTO();
		emailDTO.setId(1);
		emailDTO.setEmail("test@gmail.com");
		emailDTO.setUser(userDTO);
		emailDTO2 = new EmailDTO();
		emailDTO2.setId(2);
		emailDTO2.setEmail("test2@gmail.com");
		emailDTO2.setUser(userDTO);
		
		entityModel = EntityModel.of(emailDTO);
		entityModel.add(linkTo(methodOn(EmailController.class)
				.getEmailById(entityModel.getContent().getId()))
			.withSelfRel());
	
		entityModel.add(linkTo(methodOn(EmailController.class)
			.getEmailByOwnerId(entityModel.getContent().getUser().getId()))
		.withRel("inUserEmail"));
		entityModel2 = EntityModel.of(emailDTO2);
		entityModel2.add(linkTo(methodOn(EmailController.class)
				.getEmailById(entityModel2.getContent().getId()))
			.withSelfRel());
	
		entityModel2.add(linkTo(methodOn(EmailController.class)
			.getEmailByOwnerId(entityModel2.getContent().getUser().getId()))
		.withRel("inUserEmail"));
		
		listEmailDTO = new ArrayList<>();
		listEmailDTO.add(emailDTO);
		listEmailDTO.add(emailDTO2);
		listEntityModel = new ArrayList<>();
		
		listEntityModel.add(entityModel);
			
		collectionModel = CollectionModel.of(listEntityModel);
		collectionModel.add(linkTo(methodOn(EmailController.class)
				.getEmailByOwnerId(emailDTO.getUser().getId()))
			.withSelfRel().withHref("http://localhost:/api/email/emailByOwnerId/1"));
		
	}	
	
	@Test
	public void shouldReturnHal_JsonContentTypeWhenAddingEmail() throws URISyntaxException, Exception {
		when(mapper.save(Mockito.any(EmailRequest.class))).thenReturn(emailDTO);
		
		when(assembler.toModel(emailDTO)).thenReturn(entityModel);
		
		mvc.perform(post(new URI("/api/email/add-email"))
			.content("{\n\"email\": \"testing@gmail.com\",\n\"username\": \"longusername\"\n}")
			.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaTypes.HAL_JSON));
				
	}
	
	@Test
	public void shouldReturnExpectedDtoOutputsWhenAddingEmail() throws URISyntaxException, Exception {
		when(mapper.save(Mockito.any(EmailRequest.class))).thenReturn(emailDTO);
		
		when(assembler.toModel(emailDTO)).thenReturn(entityModel);
		
		mvc.perform(post(new URI("/api/email/add-email"))
			.content("{\n\"email\": \"test@gmail.com\",\n\"username\": \"testusername\"\n}")
			.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("id",
				equalTo(emailDTO.getId())))
		.andExpect(jsonPath("email",
				equalTo(emailDTO.getEmail())))
		.andExpect(jsonPath("user.id",
				equalTo(emailDTO.getUser().getId())))
		.andExpect(jsonPath("user.username",
				equalTo(emailDTO.getUser().getUsername())))
		.andExpect(jsonPath("user.totalEmails",
				equalTo(emailDTO.getUser().getTotalEmails())))
		.andExpect(jsonPath("user.totalTodos",
				equalTo(emailDTO.getUser().getTotalTodos())));
	}
	
	@Test
	public void shouldReturnExpectedNestedUserLinkOutputsWhenAddingEmail() throws URISyntaxException, Exception {
		when(mapper.save(Mockito.any(EmailRequest.class))).thenReturn(emailDTO);
		
		when(assembler.toModel(emailDTO)).thenReturn(entityModel);
		
		mvc.perform(post(new URI("/api/email/add-email"))
			.content("{\n\"email\": \"test@gmail.com\",\n\"username\": \"testusername\"\n}")
			.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("user._links",
				isA(Map.class)))
		.andExpect(jsonPath("user._links.inUserById.href",
				endsWith("/api/user/userById/1")))
		.andExpect(jsonPath("user._links.inUserByUsername.href",
				endsWith("/api/user/userByUsername?username=testusername")));
	}
	
	@Test
	public void shouldReturnHal_JsonContentType() throws URISyntaxException, Exception { 
		when(mapper.getById(1)).thenReturn(emailDTO);
		
		when(assembler.toModel(emailDTO)).thenReturn(entityModel);
		
		mvc.perform(get(new URI("/api/email/emailById?id=1")))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaTypes.HAL_JSON));
				
	}
	
	@Test
	@DisplayName("Should ReturnExpectedJsonOutput When GetMappingEmailById")
	public void shouldReturnExpectedDtoOutputs() throws URISyntaxException, Exception { 
		when(mapper.getById(1)).thenReturn(emailDTO);
		
		when(assembler.toModel(emailDTO)).thenReturn(entityModel);
		
		mvc.perform(get(new URI("/api/email/emailById?id=1")))
		.andExpect(jsonPath("id",
				equalTo(emailDTO.getId())))
		.andExpect(jsonPath("email", 
				equalTo(emailDTO.getEmail())))
		.andExpect(jsonPath("user.id", 
				equalTo(emailDTO.getUser().getId())))
		.andExpect(jsonPath("user.username",
				equalTo(emailDTO.getUser().getUsername())))
		.andExpect(jsonPath("user.totalEmails",
				equalTo(emailDTO.getUser().getTotalEmails())))
		.andExpect(jsonPath("user.totalTodos",
				equalTo(emailDTO.getUser().getTotalTodos())));
	}
	
	@Test
	@DisplayName("Should ReturnExpectedLinkOutput When GetMappingEmailById")
	public void shouldExpectedLinkOutputs() throws URISyntaxException, Exception {
		when(mapper.getById(1)).thenReturn(emailDTO);
		
		when(assembler.toModel(emailDTO)).thenReturn(entityModel);
		
		mvc.perform(get(new URI("/api/email/emailById?id=1")))
		.andExpect(jsonPath("_links.self.href", 
				endsWith("/api/email/emailById?id=1")));
	}
	
	@Test
	@DisplayName("Should ReturnExpectedNestedUserLinkOutput When GetMappingEmailById")
	public void shouldExpectedNestedUserLinkOutputs() throws URISyntaxException, Exception {
		when(mapper.getById(1)).thenReturn(emailDTO);
		
		when(assembler.toModel(emailDTO)).thenReturn(entityModel);
		
		mvc.perform(get(new URI("/api/email/emailById"))
				.param("id", "1"))
		.andExpect(jsonPath("user._links.inUserById.href", 
				endsWith("/api/user/userById/1")))
		.andExpect(jsonPath("user._links.inUserByUsername.href", 
				endsWith("/api/user/userByUsername?username=testusername")));
	}
	
	@Test
	@DisplayName("Should ReturnExpectedDtoOutputCollection When GetMappingEmailByOwnerId")
	public void shouldExpectedDtoOutputsByCollection() throws URISyntaxException, Exception {
		when(mapper.getAllEmailByUsersId(1)).thenReturn(listEmailDTO);
		
		when(assembler.toCollectionModel(listEmailDTO)).thenReturn(collectionModel);
		
		mvc.perform(get(new URI("/api/email/emailByOwnerId/1")))
		.andExpect(jsonPath("_embedded.emailDTOList[0].id", 
				equalTo(emailDTO.getId())))
		.andExpect(jsonPath("_embedded.emailDTOList[0].email", 
				equalTo(emailDTO.getEmail())))
		.andExpect(jsonPath("_embedded.emailDTOList[0].user.id", 
				equalTo(emailDTO.getUser().getId())))
		.andExpect(jsonPath("_embedded.emailDTOList[0].user.username", 
				equalTo(emailDTO.getUser().getUsername())))
		.andExpect(jsonPath("_embedded.emailDTOList[0].user.totalEmails", 
				equalTo(emailDTO.getUser().getTotalEmails())))
		.andExpect(jsonPath("_embedded.emailDTOList[0].user.totalTodos", 
				equalTo(emailDTO.getUser().getTotalTodos())));
	}
	
	@Test
	@DisplayName("Should ReturnExpectedLinkCollection When GetMappingEmailByOwnerId")
	public void shouldExpectedLinkCollection() throws URISyntaxException, Exception {
		when(mapper.getAllEmailByUsersId(1)).thenReturn(listEmailDTO);
		
		when(assembler.toCollectionModel(listEmailDTO)).thenReturn(collectionModel);
		
		mvc.perform(get(new URI("/api/email/emailByOwnerId/1")))
		.andExpect(jsonPath("_embedded.emailDTOList[0]._links.self.href", 
				endsWith("/api/email/emailById?id=1")))
		.andExpect(jsonPath("_embedded.emailDTOList[0]._links.inUserEmail.href", 
				endsWith("/api/email/emailByOwnerId/1")))
		.andExpect(jsonPath("_links.self.href", 
				endsWith("/api/email/emailByOwnerId/1")));
	}
	
	@Test
	@DisplayName("Should ReturnExpectedNestedUserLinkCollection When GetMappingEmailByOwnerId")
	public void shouldExpectedNestedUserLinkCollection() throws URISyntaxException, Exception {
		when(mapper.getAllEmailByUsersId(1)).thenReturn(listEmailDTO);
		
		when(assembler.toCollectionModel(listEmailDTO)).thenReturn(collectionModel);
		
		mvc.perform(get(new URI("/api/email/emailByOwnerId/1")))
		.andExpect(jsonPath("_embedded.emailDTOList[0].user._links.inUserById.href", 
				endsWith("/api/user/userById/1")))
		.andExpect(jsonPath("_embedded.emailDTOList[0].user._links.inUserByUsername.href", 
				endsWith("/api/user/userByUsername?username=testusername")));
	}
	
}
