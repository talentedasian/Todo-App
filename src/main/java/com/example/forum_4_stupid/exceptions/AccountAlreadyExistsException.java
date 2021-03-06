package com.example.forum_4_stupid.exceptions;

public class AccountAlreadyExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountAlreadyExistsException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccountAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public AccountAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AccountAlreadyExistsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AccountAlreadyExistsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
