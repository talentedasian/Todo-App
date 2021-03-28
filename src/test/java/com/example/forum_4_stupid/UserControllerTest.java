package com.example.forum_4_stupid;
 
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;

import org.checkerframework.checker.units.qual.s;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.forum_4_stupid.controller.UserController;
import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.dtoMapper.UserDtoMapper;
import com.example.forum_4_stupid.hateoas.UserDTOAssembler;
import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.UsersRepository;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserDtoMapper mapper;
	@MockBean
	private UserDTOAssembler assembler;
	@MockBean
	private UsersRepository repo;
	@Autowired
	private WebApplicationContext context;
	@MockBean
	private UserDetailsService service;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.alwaysDo(MockMvcResultHandlers.print())
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}
	
	@Before
	public void setUpRepo() {
		Users user = new Users(1, "test", Instant.now(), "testpassword", true,null, null);
		context.getBean(UsersRepository.class).save(user);
	}
	
	@Test
	public void assertThatUserShouldReturnStatusOk() throws URISyntaxException, Exception {
		var userDTO = new UserDTO();
		userDTO.setId(1);
		userDTO.setUsername("test");
		userDTO.setTotalEmails(0);
		userDTO.setTotalTodos(0);
//		when(mapper.getById(1)).thenReturn(userDTO);
//		EntityModel<UserDTO> ass = Mockito.spy(UserDTOAssembler.class).toModel(userDTO);
//		when(assembler.toModel(userDTO)).thenReturn(ass);
		mockMvc.perform(MockMvcRequestBuilders.get(new URI("/api/user/userById/1"))
				.content("utf-8"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
