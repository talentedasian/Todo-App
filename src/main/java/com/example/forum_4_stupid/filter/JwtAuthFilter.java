package com.example.forum_4_stupid.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;

import com.example.forum_4_stupid.JwtKeys;
import com.example.forum_4_stupid.LoggerClass;
import com.example.forum_4_stupid.exceptions.ExceptionMessageModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;

import java.io.IOException;

import javax.servlet.Filter;

public class JwtAuthFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request; 
		String[] path = req.getServletPath().split("/");
		long seconds = 3 * 60;
		
		try {
				//CHECKS IF USER ACCESSED PROTECTED RESOURCE
			if (req.getRequestURI().subSequence(0, 4).equals("/api")) {
				try {
					Claims jwt = Jwts.parserBuilder()
							.setSigningKey(JwtKeys.getSigningKey())
							.setAllowedClockSkewSeconds(seconds)
							.build()
							.parseClaimsJws(req.getHeader("Authorization")).getBody();
					//CHECKS IF USER HAS ACCESSED PROTECTED RESOURCE 
					if(req.getMethod().equalsIgnoreCase("get")) {
						if(path[2].equalsIgnoreCase("user")) {
							if (req.getParameter("username") != null) {
								if (!req.getParameter("username").equals(jwt.getSubject().toString())) {
									handleIllegalAccessOfResourceException(res);
									return;							
								} 
							} else {
								if (!path[4].equals(jwt.getId())) {
									handleIllegalAccessOfResourceException(res);
									return;
								}
							}
						} else if(path[2].equalsIgnoreCase("email")) {
							if (req.getParameter("id") != null) {
																	
								} else {
									if (!path[4].equals(jwt.getId())) {
										handleIllegalAccessOfResourceException(res);
										return;
									}
							}
						}
					}
				} catch (IllegalArgumentException e) {
					handleIllegalArgumentException(res);
					return;
				} catch (ExpiredJwtException e) {
					handleExpiredJwtException(res);
					return;
				} catch (SignatureException e) {
					handleInvalidSignatureJwtException(res);
					return;
				}
			}			
		} catch (StringIndexOutOfBoundsException | NullPointerException e) {
			
		}
			
		chain.doFilter(req, res);
	}
	
	private void handleIllegalArgumentException (HttpServletResponse res) throws IOException {
		LoggerClass.getLogger(JwtAuthFilter.class).log(Level.INFO, "User accessed protected resource without proper authorization");
		ExceptionMessageModel exceptionMessage = new ExceptionMessageModel();
		exceptionMessage.setErr("401");
		exceptionMessage.setReason("No JWT Found on Authorization Header");
		String json = new ObjectMapper().writeValueAsString(exceptionMessage);
		res.setContentType("application/json");
		res.setStatus(401);
		res.getWriter().write(json);
	}
	
	private void handleIllegalAccessOfResourceException (HttpServletResponse res) throws IOException {
		LoggerClass.getLogger(JwtAuthFilter.class).log(Level.INFO, "User accessed resource from another user");
		ExceptionMessageModel exceptionMessage = new ExceptionMessageModel();
		exceptionMessage.setErr("401");
		exceptionMessage.setReason("Illegal Access of Resource");
		String json = new ObjectMapper().writeValueAsString(exceptionMessage);
		res.setContentType("application/json");
		res.setStatus(401);
		res.getWriter().write(json);
	}
	
	private void handleExpiredJwtException (HttpServletResponse res) throws IOException {
		LoggerClass.getLogger(JwtAuthFilter.class).log(Level.INFO, "User accessed protected resource with expired JWT");
		ExceptionMessageModel exceptionMessage = new ExceptionMessageModel();
		exceptionMessage.setErr("401");
		exceptionMessage.setReason("Expired JWT");
		String json = new ObjectMapper().writeValueAsString(exceptionMessage);
		res.setContentType("application/json");
		res.setStatus(401);
		res.getWriter().write(json);
	}
	
	private void handleInvalidSignatureJwtException (HttpServletResponse res) throws IOException {
		LoggerClass.getLogger(JwtAuthFilter.class).log(Level.INFO, "User accessed protected resource with invalid signature of JWT. Server Might have Restarted");
		ExceptionMessageModel exceptionMessage = new ExceptionMessageModel();
		exceptionMessage.setErr("401");
		exceptionMessage.setReason("Invalid Signature of JWT");
		exceptionMessage.setOptional("Server might have restarted or JWT was tampered");
		String json = new ObjectMapper().writeValueAsString(exceptionMessage);
		res.setContentType("application/json");
		res.setStatus(401);
		res.getWriter().write(json);
	}
}

