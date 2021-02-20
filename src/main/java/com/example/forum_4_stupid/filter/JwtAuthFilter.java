package com.example.forum_4_stupid.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.example.forum_4_stupid.exceptions.JwtExpiredException;
import com.example.forum_4_stupid.exceptions.JwtNotFoundException;
import com.example.forum_4_stupid.exceptions.JwtNotFromUserException;
import com.example.forum_4_stupid.service.JwtAuthenticationSuccessHandler;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtAuthFilter extends GenericFilterBean{

	//throw an appropriate error and status code
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request; 
			//checkIfJwtTokenIsNotExpired method already checks if JWT is null
		Claims jwt = Jwts.parserBuilder().setSigningKey(JwtAuthenticationSuccessHandler.getJwtKey()).build()
				.parseClaimsJws(req.getHeader("Authorization")).getBody();
		if (checkIfJwtTokenIsNotExpired(res, req)) {
			if (SecurityContextHolder.getContext().getAuthentication().getName() != jwt.getSubject()) {
				throw new AccessDeniedException("Access Denied, token is not owned by authenticated user", new JwtNotFromUserException("Jwt Token Is not From Original User"));
			}
		} else {
			throw new AccessDeniedException("Jwt Token Validation failed");			
		}
		chain.doFilter(req, res);
		
	}
	
	//do appropriate token validation!!
	private boolean checkIfJwtTokenIsNotExpired (HttpServletResponse res, HttpServletRequest req) {
		if (checkIfAuthorizationHeaderIsNull(req)) {
			Claims jwt = Jwts.parserBuilder().setSigningKey(JwtAuthenticationSuccessHandler.getJwtKey()).build()
								.parseClaimsJws(req.getHeader("Authorization")).getBody();
			Date expiry = new Date();
			if (jwt.getExpiration().toInstant().isBefore(expiry.toInstant()) && !jwt.getExpiration().toInstant().isAfter(expiry.toInstant())) {
				return true;
			}
			throw new AccessDeniedException("Access Denied token is already expired.", new JwtExpiredException("Jwt Token Is Expired Already"));
		}
		return false;
	}
	
	private boolean checkIfAuthorizationHeaderIsNull (HttpServletRequest req) {
		if (req.getHeader("Authorization") == null) {
			throw new JwtNotFoundException("No Jwt Token Is found On the Authorization Header");
		}
		return true;
	}
	

}
