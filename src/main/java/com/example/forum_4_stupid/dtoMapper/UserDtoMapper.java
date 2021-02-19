package com.example.forum_4_stupid.dtoMapper;

import com.example.forum_4_stupid.dto.UserDTO;
import com.example.forum_4_stupid.model.Todos;
import com.example.forum_4_stupid.model.Users;

public class UserDtoMapper {
	
	private TodoDtoMapper todoDtoMapper = new TodoDtoMapper();
	
	public UserDTO returnUser (Users users, Todos todos) {
		var userDTO = new UserDTO();
		userDTO.setId(users.getId());
		userDTO.setUsername(users.getUsername());
		userDTO.setTodoForeignKey(todoDtoMapper.getForeignKeyTodo(todos));
		
		return userDTO;
	}
	
	public UserDTO returnUserForeignKey (Users users) {
		var userDTO = new UserDTO();
		userDTO.setId(users.getId());
		
		return userDTO;
	}
}
