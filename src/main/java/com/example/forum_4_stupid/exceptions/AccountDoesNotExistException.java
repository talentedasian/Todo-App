package com.example.forum_4_stupid.exceptions;


public class AccountDoesNotExistException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountDoesNotExistException (String errMessage, Throwable err) {
		super(errMessage, err);
	}

	public AccountDoesNotExistException(String errMessage) {
		super(errMessage);
	}

	public AccountDoesNotExistException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
