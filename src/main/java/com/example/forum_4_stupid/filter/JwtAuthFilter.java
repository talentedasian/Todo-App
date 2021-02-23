package com.example.forum_4_stupid.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;

import com.example.forum_4_stupid.exceptions.JwtExpiredException;
import com.example.forum_4_stupid.exceptions.JwtNotFoundException;
import com.example.forum_4_stupid.exceptions.JwtNotFromUserException;
import com.example.forum_4_stupid.exceptions.ProtectedResourceException;
import com.example.forum_4_stupid.service.JwtProvider;

import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import javax.servlet.Filter;

public class JwtAuthFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request; 
		
			//checks if user has gone to a protected resource
		if (req.getRequestURI().subSequence(0, 5).equals("/user")) {
			try {
				Claims jwt = Jwts.parserBuilder().setSigningKey(JwtProvider.key().getPrivate()).build()
						.parseClaimsJws(req.getHeader("Authorization")).getBody();
				if (req.getParameter("id").equals(jwt.getId().toString())) {
					return;
				} else {
					throw new AccessDeniedException("Access Denied! User Accessed protected resouce from another user",
							new ProtectedResourceException("Unauthorized User accessed resource " + req.getRequestURL()));
				}
			} catch (IllegalArgumentException e) {
				throw new AccessDeniedException("Access Denied! User Accessed protected resource without proper authorization",
						new JwtNotFoundException("No Jwt Found on Authorization Header"));
			} catch (ExpiredJwtException e) {
				throw new AccessDeniedException("Access Denied! User accessed protected resource without proper authorization", 
						new JwtExpiredException("Jwt Token Expired"));
			} catch (ClaimJwtException e) {
				throw new AccessDeniedException("Access Denied! User accessed protected resource without proper authorization", 
						new JwtNotFromUserException("Jwt token not from user"));
			}
		}
		
		chain.doFilter(req, res);
	}
	
	
}
	
	

