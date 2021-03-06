package com.example.forum_4_stupid.exceptions;

public class EmailNotFoundByUsernameException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailNotFoundByUsernameException () {
		super();
	}
	
	public EmailNotFoundByUsernameException (String errMessage, Throwable err) {
		super(errMessage, err);
	}

}
