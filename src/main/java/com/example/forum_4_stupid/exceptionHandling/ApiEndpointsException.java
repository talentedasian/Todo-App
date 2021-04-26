package com.example.forum_4_stupid.exceptionHandling;

import java.lang.reflect.Field;
import java.time.DateTimeException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.forum_4_stupid.exceptions.AccountDoesNotExistException;
import com.example.forum_4_stupid.exceptions.BadRequestException;
import com.example.forum_4_stupid.exceptions.ExceptionMessageModel;
import com.example.forum_4_stupid.exceptions.LoginFailedException;
import com.example.forum_4_stupid.exceptions.PhoneNumberLimitHasReachedException;
import com.example.forum_4_stupid.exceptions.PhoneNumberNotFoundByUsernameException;
import com.example.forum_4_stupid.exceptions.TodoAlreadyExistException;
import com.example.forum_4_stupid.exceptions.TodoNotFoundException;
import com.example.forum_4_stupid.exceptions.TodoNotSendableNoPhoneNumberAssociatedOnUser;

@RestControllerAdvice
public class ApiEndpointsException extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ExceptionMessageModel> handleDataIntegrityException() { 
		ExceptionMessageModel exceptionMessage = new ExceptionMessageModel();
		exceptionMessage.setErr("409");
		exceptionMessage.setReason("EntityAlreadyExists");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return new ResponseEntity<ExceptionMessageModel>(exceptionMessage, headers, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(AccountDoesNotExistException.class)
	public ResponseEntity<ExceptionMessageModel> handleAccountDoesNotExistException() { 
		ExceptionMessageModel exceptionMessage = new ExceptionMessageModel();
		exceptionMessage.setErr("404");
		exceptionMessage.setReason("Account Does Not Exist");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return new ResponseEntity<ExceptionMessageModel>(exceptionMessage, headers, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PhoneNumberNotFoundByUsernameException.class)
	public ResponseEntity<ExceptionMessageModel> handlePhoneNumberDoesNotExistException() { 
		ExceptionMessageModel exceptionMessage = new ExceptionMessageModel();
		exceptionMessage.setErr("404");
		exceptionMessage.setReason("PhoneNumber Does Not Exist");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return new ResponseEntity<ExceptionMessageModel>(exceptionMessage, headers, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PhoneNumberLimitHasReachedException.class)
	public ResponseEntity<ExceptionMessageModel> handlePhoneNumberLimitReachedException() {
		ExceptionMessageModel exceptionMessage = new ExceptionMessageModel();
		exceptionMessage.setErr("403");
		exceptionMessage.setReason("PhoneNumber limit per user reached");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return new ResponseEntity<ExceptionMessageModel>(exceptionMessage, headers, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(TodoNotFoundException.class)
	public ResponseEntity<ExceptionMessageModel> handleTodoDoesNotExistException() { 
		ExceptionMessageModel exceptionMessage = new ExceptionMessageModel();
		exceptionMessage.setErr("404");
		exceptionMessage.setReason("Todo does not exist");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return new ResponseEntity<ExceptionMessageModel>(exceptionMessage, headers, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TodoAlreadyExistException.class)
	public ResponseEntity<ExceptionMessageModel> handleTodoAlreadyExistException() {
		ExceptionMessageModel exceptionMessage = new ExceptionMessageModel();
		exceptionMessage.setErr("409");
		exceptionMessage.setReason("Todo Already Exist");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return new ResponseEntity<ExceptionMessageModel>(exceptionMessage, headers, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(LoginFailedException.class)
	public ResponseEntity<ExceptionMessageModel> handleLoginFailedException(LoginFailedException ex) { 
		ExceptionMessageModel exceptionMessage = new ExceptionMessageModel();
		exceptionMessage.setErr("401");
		exceptionMessage.setReason("Login failed");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if (ex.getCause() instanceof AccountDoesNotExistException) {
			exceptionMessage.setErr("401");
			exceptionMessage.setReason("Login Failed Due to Non-Existent Account");
			
			return new ResponseEntity<ExceptionMessageModel>(exceptionMessage, headers, HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<ExceptionMessageModel>(exceptionMessage, headers, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ExceptionMessageModel> handleBadRequestLogin(LoginFailedException ex) { 
		ExceptionMessageModel exceptionMessage = new ExceptionMessageModel();
		exceptionMessage.setErr("400");
		exceptionMessage.setReason("Bad Request");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return new ResponseEntity<ExceptionMessageModel>(exceptionMessage, headers, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DateTimeException.class)
	public ResponseEntity<ExceptionMessageModel> handleBadRequestDateTime(Exception ex) {
		ExceptionMessageModel exceptionMessage = new ExceptionMessageModel();
		exceptionMessage.setErr("400");
		exceptionMessage.setReason("Bad Request");
		exceptionMessage.setOptional("Invalid Date");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return new ResponseEntity<ExceptionMessageModel>(exceptionMessage, headers, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(TodoNotSendableNoPhoneNumberAssociatedOnUser.class)
	public void handleNoPhoneNumberToSendTo() throws IllegalArgumentException, IllegalAccessException { 
		ExceptionMessageModel exceptionMessage = new ExceptionMessageModel();
		exceptionMessage.setErr("400");
		exceptionMessage.setReason("No Phone Number associated on user");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
	}
	
}
