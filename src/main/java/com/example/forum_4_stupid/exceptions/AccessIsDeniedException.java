package com.example.forum_4_stupid.exceptions;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccessIsDeniedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccessIsDeniedException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccessIsDeniedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AccessIsDeniedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
