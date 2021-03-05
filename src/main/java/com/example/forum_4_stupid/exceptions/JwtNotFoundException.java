package com.example.forum_4_stupid.exceptions;

public class JwtNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JwtNotFoundException(String message) {
		super(message);
	}

	public JwtNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

}
