package com.example.forum_4_stupid.exceptionHandling;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.forum_4_stupid.exceptions.AccessIsDeniedException;
import com.example.forum_4_stupid.exceptions.AccountDoesNotExistException;
import com.example.forum_4_stupid.exceptions.JwtNotFoundException;
import com.sun.net.httpserver.Headers;

@RestControllerAdvice
public class JwtGlocalExceptionHandling extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<Map<String,String>> jwtGlobalException (AccessIsDeniedException ex) {
		Map<String,String> errResponse = new HashMap<>();
		
		if (ex.getCause() instanceof JwtNotFoundException) {
			errResponse.put("code", "401");
			errResponse.put("reason", "No JWT Provided");
			return new ResponseEntity<Map<String,String>>(errResponse, null, HttpStatus.FORBIDDEN);
		}
		
		return new ResponseEntity<Map<String,String>>(errResponse, null, HttpStatus.OK);
	}
	
	@ExceptionHandler(AccountDoesNotExistException.class)
	public ResponseEntity<Map<String, String>> handleDataIntegrityException (AccountDoesNotExistException ex, WebRequest request) { 
		Map<String, String> errResponse = new HashMap<>();
		errResponse.put("code", "401");
		errResponse.put("reason", "Account Already Exists");
		
		return new ResponseEntity<Map<String, String>>(errResponse, new HttpHeaders(), HttpStatus.CONFLICT);
	}
}
