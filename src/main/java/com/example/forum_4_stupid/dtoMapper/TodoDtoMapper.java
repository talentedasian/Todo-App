package com.example.forum_4_stupid.dtoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.dto.TodoDTO;
import com.example.forum_4_stupid.dto.TodoRequest;
import com.example.forum_4_stupid.dtoMapper.interfaces.PersistentDTOMapper;
import com.example.forum_4_stupid.model.Todos;
import com.example.forum_4_stupid.service.TodoService;

@Component
public class TodoDtoMapper implements PersistentDTOMapper<X, Z>{

	@Autowired
	private TodoService todoService;
	
	@Override
	public TodoDTO returnEntity(Todos entity) {
		var todoDTO = new TodoDTO();
		todoDTO.setId(entity.getId());
		todoDTO.setContent(entity.getContent());
		todoDTO.setCreatedAt(entity.getCreated());
		todoDTO.setDeadline(entity.getDeadline());
		todoDTO.setTitle(entity.getTitle());
		
		return todoDTO;
	}

}
