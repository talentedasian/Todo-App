package com.example.forum_4_stupid.exceptions;

import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class AccountDoesNotExistException extends RuntimeException {
	
	public AccountDoesNotExistException (String errMessage, Throwable err) {
		super(errMessage, err);
	}

	public AccountDoesNotExistException() {
		super();
	}
	
	
	
}
