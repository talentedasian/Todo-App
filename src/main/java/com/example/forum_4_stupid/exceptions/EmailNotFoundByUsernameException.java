package com.example.forum_4_stupid.exceptions;

public class EmailNotFoundByUsernameException extends RuntimeException {
	
	public EmailNotFoundByUsernameException () {
		super();
	}
	
	public EmailNotFoundByUsernameException (String errMessage, Throwable err) {
		super(errMessage, err);
	}

}
