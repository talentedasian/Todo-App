package com.example.forum_4_stupid.exceptions;

public class JwtExpiredException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JwtExpiredException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
