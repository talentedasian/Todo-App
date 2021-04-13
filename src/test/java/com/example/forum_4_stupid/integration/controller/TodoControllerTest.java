package com.example.forum_4_stupid.integration.controller;

import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.forum_4_stupid.controller.TodoController;
import com.example.forum_4_stupid.dto.TodoDTO;
import com.example.forum_4_stupid.dto.TodoRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.TodoDtoMapper;
import com.example.forum_4_stupid.exceptionHandling.ApiEndpointsException;
import com.example.forum_4_stupid.hateoas.TodoDTOAssembler;
import com.example.forum_4_stupid.repository.TodosRepository;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = { TodoController.class, ApiEndpointsException.class })
@AutoConfigureMockMvc(print = MockMvcPrint.DEFAULT, addFilters = false, printOnlyOnFailure = false)
public class TodoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TodoDtoMapper mapper;
	@MockBean
	private TodoDTOAssembler assembler;
	@MockBean
	private TodosRepository todoRepo;
	@MockBean
	private UsersRepository usersRepo;
	
	private final Map<String, String> content = new HashMap<>();
	private TodoDTO todoDTO;
	private final UserDTO userDTO = new UserDTO(1, "testusername", 0, 1);
	private EntityModel<TodoDTO> entityModel;
	private String jsonContent;
	private static final LocalDateTime timeNow = LocalDateTime.now();
	private static final LocalDateTime timeDeadline = LocalDateTime.of(2021, 4, 9, 13, 22).minusSeconds(0L);
	
	@BeforeEach
	public void setUp() throws JsonProcessingException {
		todoDTO = new TodoDTO(1, "test content long enough", "test title", 
				timeNow, timeDeadline, userDTO);
		
		entityModel = EntityModel.of(todoDTO);
		
		content.put("title", "test title ");
		content.put("content", "test content long enough ");
		content.put("username", "testusername");
		content.put("year", "2021");
		content.put("month", "4");
		content.put("day", "9");
		content.put("hour", "13");
		content.put("minute", "22");
		
		jsonContent = new ObjectMapper().writeValueAsString(content);
	}
	
	@Test
	public void shouldReturnStatusIsCreated() throws URISyntaxException, Exception {
		when(mapper.save(any(TodoRequest.class))).thenReturn(todoDTO);
		when(assembler.toModel(todoDTO)).thenReturn(entityModel);
		mockMvc.perform(post(new URI("/api/todo/add-todo"))
				.content(jsonContent)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("should return hal json As ContentType when Adding Email")
	public void shouldReturnHal_Json() throws Exception {
		when(mapper.save(any(TodoRequest.class))).thenReturn(todoDTO);
		
		when(assembler.toModel(Mockito.any())).thenReturn(entityModel);
		
		mockMvc.perform(post(new URI("/api/todo/add-todo"))
				.content(jsonContent)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaTypes.HAL_JSON));
	}
	
	@Test
	public void shouldReturnBadRequestWhenInvalidDate() throws Exception {
		when(mapper.save(Mockito.any(TodoRequest.class))).thenThrow(new DateTimeException(""));
		
		when(assembler.toModel(Mockito.any())).thenReturn(entityModel);
		
		mockMvc.perform(post(new URI("/api/todo/add-todo"))
				.content(jsonContent)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("err", equalTo("400")))
		.andExpect(jsonPath("reason", equalTo("Bad Request")))
		.andExpect(jsonPath("optional", equalTo("Invalid Date")));
	}
	
	@Test
	@DisplayName("should return excpected dto output when GetMapping by ID")
	public void shouldReturnExpectedDtoOutputs() throws URISyntaxException, Exception {
		when(mapper.getById(1)).thenReturn(todoDTO);
		
		when(assembler.toModel(todoDTO)).thenReturn(entityModel);
		
		mockMvc.perform(get(new URI("/api/todo/todoById/1"))
				.content(jsonContent)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("id",
				equalTo(todoDTO.getId())))
		.andExpect(jsonPath("content",
				equalTo(todoDTO.getContent())))
		.andExpect(jsonPath("title",
				equalTo(todoDTO.getTitle())))
		.andExpect(jsonPath("createdAt",
				equalTo(timeNow.toString())))
		.andExpect(jsonPath("deadline",
				equalTo(timeDeadline.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))));
	}
	
	@Test
	@DisplayName("shoudl return nested user dto outputs when adding email")
	public void shouldReturnExpectedNestedUserDtoOutputs() throws URISyntaxException, Exception {
		when(mapper.save(any(TodoRequest.class))).thenReturn(todoDTO);
		
		when(assembler.toModel(Mockito.any())).thenReturn(entityModel);
		
		mockMvc.perform(post(new URI("/api/todo/add-todo"))
				.content(jsonContent)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("user.id", 
				equalTo(userDTO.getId())))
		.andExpect(jsonPath("user.username", 
				equalTo(userDTO.getUsername())))
		.andExpect(jsonPath("user.totalEmails", 
				equalTo(userDTO.getTotalEmails())))
		.andExpect(jsonPath("user.totalTodos", 
				equalTo(userDTO.getTotalTodos())));
	}
	
	@Test
	@DisplayName("shoudl return nested user dto outputs when GetMapping by ID")
	public void shouldReturnExpectedNestedUserDtoOutputs2() throws URISyntaxException, Exception {
		when(mapper.getById(1)).thenReturn(todoDTO);
		
		when(assembler.toModel(todoDTO)).thenReturn(entityModel);
		
		mockMvc.perform(get(new URI("/api/todo/todoById//1"))
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("user.id", 
				equalTo(userDTO.getId())))
		.andExpect(jsonPath("user.username", 
				equalTo(userDTO.getUsername())))
		.andExpect(jsonPath("user.totalEmails", 
				equalTo(userDTO.getTotalEmails())))
		.andExpect(jsonPath("user.totalTodos", 
				equalTo(userDTO.getTotalTodos())));
	}
	
	@Test
	@DisplayName("should return nested user link outputs when adding email")
	public void shouldReturnExpectedNestedUserLinkOutputs() throws URISyntaxException, Exception {
		when(mapper.save(any(TodoRequest.class))).thenReturn(todoDTO);
		
		when(assembler.toModel(Mockito.any())).thenReturn(entityModel);
		
		mockMvc.perform(post(new URI("/api/todo/add-todo"))
				.content(jsonContent)
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("user._links.inUserById.href", 
				endsWith("/api/user/userById/" + userDTO.getId())))
		.andExpect(jsonPath("user._links.inUserByUsername.href", 
				endsWith("/api/user/userByUsername?username=" + userDTO.getUsername())));
	}
	
	@Test
	@DisplayName("should return nested user links outputs when GetMapping by ID")
	public void shouldReturnExpectedNestedUserLinkOutputs2() throws URISyntaxException, Exception {
		when(mapper.getById(1)).thenReturn(todoDTO);
		
		when(assembler.toModel(todoDTO)).thenReturn(entityModel);
		
		mockMvc.perform(get(new URI("/api/todo/todoById//1"))
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("user._links.inUserById.href", 
				endsWith("/api/user/userById/" + userDTO.getId())))
		.andExpect(jsonPath("user._links.inUserByUsername.href", 
				endsWith("/api/user/userByUsername?username=" + userDTO.getUsername())));
	}
	
}
