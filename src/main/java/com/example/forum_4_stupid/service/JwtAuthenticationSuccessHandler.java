package com.example.forum_4_stupid.service;

import java.io.IOException;
import java.security.Key;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.LoggerClass;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
	}
	
	public static Key getJwtKey () {
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		return key;
	}
	
	protected void handle (HttpServletRequest request, HttpServletResponse response) {
		if (response.isCommitted()) {
			LoggerClass.getLogger().log(Level.DEBUG, "Response already has headers and status codes");
		}
		
		Cookie cookie = new Cookie("auth", jwtLogin());
		response.addCookie(cookie);
	}
	
	private String jwtLogin () {
		String jws = Jwts.builder()
				.setSubject(SecurityContextHolder.getContext().getAuthentication().getName())
				.setExpiration(new Date(System.currentTimeMillis() + 43200000))
				.signWith(getJwtKey)
				.compact();
		
		return jws;		
	}

}
