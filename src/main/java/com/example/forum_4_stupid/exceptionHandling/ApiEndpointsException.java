package com.example.forum_4_stupid.exceptionHandling;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.forum_4_stupid.exceptions.AccountDoesNotExistException;
import com.example.forum_4_stupid.exceptions.EmailNotFoundByUsernameException;
import com.example.forum_4_stupid.exceptions.TodoAlreadyExistException;
import com.example.forum_4_stupid.exceptions.TodoNotFoundException;

@RestControllerAdvice
public class ApiEndpointsException extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, String>> handleDataIntegrityException () { 
		Map<String, String> errResponse = new HashMap<>();
		errResponse.put("code", "409");
		errResponse.put("reason", "Entity Already Exists");
		
		return new ResponseEntity<Map<String, String>>(errResponse, new HttpHeaders(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(AccountDoesNotExistException.class)
	public ResponseEntity<Map<String, String>> handleAccountDoesNotExistException () { 
		Map<String, String> errResponse = new HashMap<>();
		errResponse.put("code", "404");
		errResponse.put("reason", "Account Does Not Exist");
		
		return new ResponseEntity<Map<String, String>>(errResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(EmailNotFoundByUsernameException.class)
	public ResponseEntity<Map<String, String>> handleEmailDoesNotExistException () { 
		Map<String, String> errResponse = new HashMap<>();
		errResponse.put("code", "404");
		errResponse.put("reason", "Email Does Not Exist");
		
		return new ResponseEntity<Map<String, String>>(errResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(TodoNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleTodoDoesNotExistException () { 
		Map<String, String> errResponse = new HashMap<>();
		errResponse.put("code", "404");
		errResponse.put("reason", "Todo Does Not Exist");
		
		return new ResponseEntity<Map<String, String>>(errResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TodoAlreadyExistException.class)
	public ResponseEntity<Map<String, String>> handleTodoAlreadyExistException () { 
		Map<String, String> errResponse = new HashMap<>();
		errResponse.put("code", "409");
		errResponse.put("reason", "Todo Already Exist");
		
		return new ResponseEntity<Map<String, String>>(errResponse, new HttpHeaders(), HttpStatus.CONFLICT);
	}
	
}
