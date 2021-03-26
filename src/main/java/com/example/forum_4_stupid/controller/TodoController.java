package com.example.forum_4_stupid.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.dto.TodoDTO;
import com.example.forum_4_stupid.dto.TodoRequest;
import com.example.forum_4_stupid.dtoMapper.TodoDtoMapper;
import com.example.forum_4_stupid.hateoas.TodoDTOAssembler;
import com.example.forum_4_stupid.utility.NestedDTOAssembler;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

	private final TodoDtoMapper todoDtoMapper;
	private final TodoDTOAssembler todoAssembler;
	
	@Autowired
	public TodoController (TodoDtoMapper todoDtoMapper, TodoDTOAssembler todoAssembler) {
		this.todoDtoMapper = todoDtoMapper;
		this.todoAssembler = todoAssembler;
	}
	
	//add appropriate redirects
	@PostMapping("/add-todo")
	public void addTodo (@Valid @RequestBody TodoRequest todoRequest) {
		todoDtoMapper.save(todoRequest);
	}
	
	@GetMapping("/todoById/{id}")
	public ResponseEntity<EntityModel<TodoDTO>> getTodoById(@PathVariable Integer id) {
		TodoDTO todo = todoDtoMapper.getById(id);
		EntityModel<TodoDTO> assembler = todoAssembler.toModel(todo); 
		var utilityMethod = new NestedDTOAssembler();
		utilityMethod.addUserFromTodoNestedEntityLink(assembler);
		
		return new ResponseEntity<>(assembler, HttpStatus.OK);
	}
	
	@GetMapping("/todoByOwnerId")
	public ResponseEntity<CollectionModel<EntityModel<TodoDTO>>> getTodoByUserId (@RequestParam Integer id) {
		List<TodoDTO> todo = todoDtoMapper.getAllByUserId(id);
		CollectionModel<EntityModel<TodoDTO>> assembler = todoAssembler.toCollectionModel(todo);
		var utilityMethod = new NestedDTOAssembler();
		utilityMethod.addUserFromTodoNestedEntityLink(assembler);
		
		
		return new ResponseEntity<CollectionModel<EntityModel<TodoDTO>>>(assembler, HttpStatus.OK);	
	}
	
}
