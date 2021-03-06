package com.example.forum_4_stupid.exceptionHandling;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.forum_4_stupid.exceptions.AccountDoesNotExistException;

@RestControllerAdvice
public class JwtGlocalExceptionHandling extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(AccountDoesNotExistException.class)
	public ResponseEntity<Map<String, String>> handleDataIntegrityException (AccountDoesNotExistException ex, WebRequest request) { 
		Map<String, String> errResponse = new HashMap<>();
		errResponse.put("code", "401");
		errResponse.put("reason", "Account Already Exists");
		
		return new ResponseEntity<Map<String, String>>(errResponse, new HttpHeaders(), HttpStatus.CONFLICT);
	}
}
