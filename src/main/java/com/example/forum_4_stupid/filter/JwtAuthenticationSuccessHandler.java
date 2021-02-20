package com.example.forum_4_stupid.filter;

import java.security.Key;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtAuthenticationSuccessHandler {

	public static Key getJwtKey () {
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		
		return key;
	}
}
