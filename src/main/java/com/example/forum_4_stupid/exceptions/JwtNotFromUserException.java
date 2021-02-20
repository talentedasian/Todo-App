package com.example.forum_4_stupid.exceptions;

public class JwtNotFromUserException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JwtNotFromUserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}
