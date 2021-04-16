package com.example.forum_4_stupid.exceptions;

public class PhoneNumberNotFoundByUsernameException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhoneNumberNotFoundByUsernameException () {
		super();
	}
	
	public PhoneNumberNotFoundByUsernameException (String errMessage, Throwable err) {
		super(errMessage, err);
	}

	public PhoneNumberNotFoundByUsernameException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public PhoneNumberNotFoundByUsernameException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public PhoneNumberNotFoundByUsernameException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
