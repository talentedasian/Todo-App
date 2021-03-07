package com.example.forum_4_stupid.exceptions;

public class TodoAlreadyExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TodoAlreadyExistException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TodoAlreadyExistException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public TodoAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public TodoAlreadyExistException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public TodoAlreadyExistException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
