package com.example.forum_4_stupid.dtoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.dto.TodoDTO;
import com.example.forum_4_stupid.dto.TodoRequest;
import com.example.forum_4_stupid.dtoMapper.interfaces.TodoDTOMapper;
import com.example.forum_4_stupid.model.Todos;
import com.example.forum_4_stupid.service.TodoService;

@Component
public class TodoDtoMapper implements TodoDTOMapper<TodoDTO,TodoRequest,Todos>{

	@Autowired
	private TodoService todoService;

	@Override
	public TodoDTO getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(TodoRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Todos entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TodoDTO getAllByUserId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TodoDTO getAllByUserUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}


}
