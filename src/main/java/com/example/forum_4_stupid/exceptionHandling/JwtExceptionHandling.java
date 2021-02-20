package com.example.forum_4_stupid.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.forum_4_stupid.exceptions.JwtNotFoundException;

@RestControllerAdvice
public class JwtExceptionHandling {

	@ExceptionHandler(value = JwtNotFoundException.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "No token provided")
	public void noJwtExceptionHandling() {
	}
	
	
}
