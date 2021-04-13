package com.example.forum_4_stupid;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.hateoas.server.core.TypeReferences.CollectionModelType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.forum_4_stupid.dto.EmailDTO;
import com.example.forum_4_stupid.dto.UserDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class DemoApplicationTests {
	
	private RestTemplate template = new RestTemplate();
	

	private final HttpHeaders headers = new HttpHeaders();
	private final String addEmailContent1 = "{\n\"email\": \"testemail@gmail.com\",\n\"username\": \"longusername\"\n}";
	private final String addEmailContent2 = "{\n\"email\": \"testemail2@gmail.com\",\n\"username\": \"longusername\"\n}";
	private final HttpEntity<String> addEmailEntity = new HttpEntity<String>(addEmailContent1, headers);
	private final HttpEntity<String> addEmailEntity2 = new HttpEntity<String>(addEmailContent2, headers);
	private final String expectedNestedUserLinkById = "/api/user/userById/1";
	private final String expectedNestedUserLinkByUsername = "/api/user/userByUsername?username=longusername";
	
	@LocalServerPort
	private int port;
	
	@BeforeEach
	public void setUp() {
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		  ObjectMapper mapper = new ObjectMapper();
		    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		    mapper.registerModule(new Jackson2HalModule());
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
	@DisplayName("should return expectded nested user link outsputs when GetMapping Email By ID")
	public void testAddEmailNestedUserLinkOutputs() throws RestClientException, URISyntaxException {
		HttpEntity<String> entity = new HttpEntity<String>("{\n\"username\": \"longusername\",\n\"password\": \"testpassword\"\n}", headers);
		
		//APPROPRIATE CALLS FOR SETTING UP A COLLECTION OF EMAILS
		template.postForEntity(new URI("http://localhost:" + port + "/auth/signup"),
						entity, UserDTO.class);
		template.exchange(new URI("http://localhost:" + port + 
						"/api/user/userById/1"), HttpMethod.GET,
						new HttpEntity<String>(null, new HttpHeaders()), UserDTO.class);
		
		HttpEntity<String> addEmailEntity = new HttpEntity<String>(addEmailContent1, headers);
		HttpEntity<String> addEmailEntity2 = new HttpEntity<String>(addEmailContent2, headers);
		
		template.postForEntity(new URI("http://localhost:" + port + "/api/email/add-email"),
						addEmailEntity, EmailDTO.class);
		template.postForEntity(new URI("http://localhost:" + port + "/api/email/add-email"),
						addEmailEntity2, EmailDTO.class);
		template.getForEntity(new URI("http://localhost:" + port + "/api/email/emailByOwnerId/1"), 
				CollectionModel.class);
		
		//SETTING UP TRAVERSON FOR USING A CLIENT-SIDE REST API CALL TO THE SERVER
		Traverson traverson = new Traverson(URI.create("http://localhost:" + port + "/api/email/emailByOwnerId/1"),
				MediaTypes.HAL_JSON);
		
		//CALLING THE API 
		String linkNestedUserById = traverson
				.follow("self")
				.toObject("$._embedded.emailDTOList[0].user._links.inUserById.href");
		String linkNestedUserByUsername = traverson
				.follow("self")
				.toObject("$._embedded.emailDTOList[0].user._links.inUserByUsername.href");
		
		//ASSERTING
		assertThat(linkNestedUserById, 
				endsWith(expectedNestedUserLinkById));
		assertThat(linkNestedUserByUsername, 
				endsWith(expectedNestedUserLinkByUsername));
		
	}
	
	
}
