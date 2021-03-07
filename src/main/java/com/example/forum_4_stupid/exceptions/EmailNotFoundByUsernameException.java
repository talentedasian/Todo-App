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

	public EmailNotFoundByUsernameException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public EmailNotFoundByUsernameException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public EmailNotFoundByUsernameException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
