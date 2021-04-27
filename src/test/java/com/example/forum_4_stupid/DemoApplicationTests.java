package com.example.forum_4_stupid;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.forum_4_stupid.dto.LoginRequest;
import com.example.forum_4_stupid.dto.PhoneNumberDTO;
import com.example.forum_4_stupid.dto.PhoneNumberRequest;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.model.PhoneNumber;
import com.example.forum_4_stupid.model.Todos;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.PhoneRepository;
import com.example.forum_4_stupid.repository.TodosRepository;
import com.example.forum_4_stupid.repository.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class DemoApplicationTests {
	
	private RestTemplate template = new RestTemplate();

	private final String expectedNestedUserLinkById = "/api/user/userById/1";
	private final String expectedNestedUserLinkByUsername = "/api/user/userByUsername?username=testusername";
	private final Users user = new Users(1, "testusername", LocalDateTime.now(), "testpassword", true, null, null);
	
	private final HttpHeaders headers = new HttpHeaders();
	
	@Autowired
	private UsersRepository userRepo;
	@Autowired
	private PhoneRepository phoneRepo;
	@Autowired
	private TodosRepository todoRepo;
	
	
	@LocalServerPort
	private int port;
	
	@BeforeEach
	public void setUp() throws JsonProcessingException {
		headers.setContentType(MediaType.APPLICATION_JSON);
	
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    mapper.registerModule(new Jackson2HalModule());
	    mapper.registerModule(new JavaTimeModule());	
	    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
	    converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
	    converter.setObjectMapper(mapper);

	    //add the new converters to the restTemplate
	    //but make sure it is BEFORE the exististing converters
	    List<HttpMessageConverter<?>> converters = template.getMessageConverters();
	    converters.add(0,converter);
	    template.setMessageConverters(converters);
			
	}

	@Test
	@DisplayName("should return expectded nested user link outsputs when GetMapping PhoneNumber By OwnerID")
	public void testAddEmailNestedUserLinkOutputs() throws RestClientException, URISyntaxException {
		var phoneNumber1 =  new PhoneNumber(2, "+63932132121", user);
		var phoneNumber2 =  new PhoneNumber(3, "+63932112143", user);
		userRepo.save(user);
		phoneRepo.save(phoneNumber1);
		phoneRepo.save(phoneNumber2);
		
		Traverson traverson = new Traverson(URI.create("http://localhost:" + port + "/api/phone/"
				+ "phoneNumberByOwnerId/1"),
				MediaTypes.HAL_JSON);
		
		String linkNestedUserById = traverson
				.follow("self")
				.toObject("$._embedded.phoneNumberDTOList[0].user._links.inUserById.href");
		String linkNestedUserByUsername = traverson
				.follow("self")
				.toObject("$._embedded.phoneNumberDTOList[0].user._links.inUserByUsername.href");
		
		assertThat(linkNestedUserById, 
				endsWith(expectedNestedUserLinkById));
		assertThat(linkNestedUserByUsername, 
				endsWith(expectedNestedUserLinkByUsername));
	}
	
	@Test
	public void testGetTodoNestedUserLinkOutputs() throws RestClientException, URISyntaxException, JsonProcessingException {
		var todos = new Todos();
		todos.setId(1);
		todos.setContent("test content long enough");
		todos.setTitle("test title long enough");
		todos.setCreated();
		todos.setUser(user);
		todos.setSendable(true);
		todoRepo.save(todos);
		
		Traverson traverson = new Traverson(new URI("http://localhost:" + port + "/api/todo/todoByOwnerId/1"),
				MediaTypes.HAL_JSON);
		
		String linkNestedUserById = traverson
				.follow("self")
				.toObject("$._embedded.todoDTOList[0].user._links.inUserById.href");
		String linkNestedUserByUsername = traverson
				.follow("self")
				.toObject("$._embedded.todoDTOList[0].user._links.inUserByUsername.href");
		
		assertThat(linkNestedUserById, 
				endsWith(expectedNestedUserLinkById));
		assertThat(linkNestedUserByUsername, 
				endsWith(expectedNestedUserLinkByUsername));
	}
	
	@Test
	public void testBadRequestAuthSignup() throws RestClientException, URISyntaxException, JsonProcessingException {
		LoginRequest entity = new LoginRequest("not", "longenough");
		
		BadRequest badRequest = assertThrows(BadRequest.class, 
				() -> template.postForObject(new URI("http://localhost:" + port + "/auth/signup"), entity, UserDTO.class));
		String badRequestResponseBody = badRequest.getResponseBodyAsString();
		Map<String, Object> errBadRequestJsonResponse = new LinkedHashMap<>();
		errBadRequestJsonResponse.put("err", "400");
		errBadRequestJsonResponse.put("reason", "Bad Request on one of the fields");
		errBadRequestJsonResponse.put("body_errors", List.of("Error on field username. size must be between 8 and 20"));
		String expectedBadRequestMessageAsString = new ObjectMapper().writeValueAsString(errBadRequestJsonResponse);
		
		assertThat(expectedBadRequestMessageAsString,
				equalTo(badRequestResponseBody));
	}
	
	@Test
	public void testBadRequestAddPhoneNumber() throws RestClientException, URISyntaxException, JsonProcessingException {
		PhoneNumberRequest entity = new PhoneNumberRequest("+639", "longenough");
		
		BadRequest badRequest = assertThrows(BadRequest.class, 
				() -> template.postForObject(new URI("http://localhost:" + port + "/api/phone/add-phoneNumber"), entity, PhoneNumberDTO.class));
		String badRequestResponseBody = badRequest.getResponseBodyAsString();
		Map<String, Object> errBadRequestJsonResponse = new LinkedHashMap<>();
		errBadRequestJsonResponse.put("err", "400");
		errBadRequestJsonResponse.put("reason", "Bad Request on one of the fields");
		errBadRequestJsonResponse.put("body_errors", List.of("Error on field phoneNumber. size must be between 11 and 20"));
		String expectedBadRequestMessageAsString = new ObjectMapper().writeValueAsString(errBadRequestJsonResponse);
		
		assertThat(expectedBadRequestMessageAsString,
				equalTo(badRequestResponseBody));
	}
	
}
