package com.example.forum_4_stupid.filter;

import java.io.IOException;
import java.security.Key;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtAuthenticationSuccessHandler implements Filter{

	public static Key getJwtKey () {
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		
		return key;
	}

//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//			Authentication authentication) throws IOException, ServletException {
//		// TODO Auto-generated method stub
//		
//		response.addCookie(new Cookie("jwtToken", jwtLogin()));
//		
//	}

	private String jwtLogin () {
		String jws = Jwts.builder()
				.setSubject(SecurityContextHolder.getContext().getAuthentication().getName())
				.setExpiration(new Date(System.currentTimeMillis() + 43200000))
				.compact();
		
		return jws;	
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
			
			chain.doFilter(request, response);
		
	}
	
}
