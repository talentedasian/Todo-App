package com.example.forum_4_stupid;

import javax.crypto.SecretKey;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtKeys {

	private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);;
	
	public static final SecretKey getSigningKey () {
		return key;
	}
	
}
