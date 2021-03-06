package com.example.forum_4_stupid.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.view.RedirectView;

import com.example.forum_4_stupid.LoggerClass;
import com.example.forum_4_stupid.exceptions.AccessIsDeniedException;
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
				Claims jwt = Jwts.parserBuilder().build()
						.parseClaimsJwt(req.getHeader("Authorization")).getBody();
				if (!req.getParameter("username").equals(jwt.getId().toString())) {
					return;
				} else {
					throw new AccessIsDeniedException("Access Denied! User Accessed protected resouce from another user",
							new ProtectedResourceException("Unauthorized User accessed resource " + req.getRequestURL() + "query parameters " + req.getQueryString()));
				}
			} catch (IllegalArgumentException e) {
				LoggerClass.getLogger(JwtAuthFilter.class).log(Level.INFO, "User accessed protected resource without proper authorization");
				handleIllegalArgumentException(res);
				return;
			} catch (ExpiredJwtException e) {
				throw new AccessIsDeniedException("Access Denied! User accessed protected resource without proper authorization", 
						new JwtExpiredException("Jwt Token Expired"));
			} catch (ClaimJwtException e) {
				throw new AccessIsDeniedException("Access Denied! User accessed protected resource without proper authorization", 
						new JwtNotFromUserException("Jwt token not from user"));
			}
		}
		chain.doFilter(req, res);
	}
	
	private void handleIllegalArgumentException (HttpServletResponse res) throws IOException {
		res.setStatus(401);
		res.getWriter().write("{\n\"code\": " + "401\n" + "\"err\": " + "\"No JWT Found on Authorizatio Header\"\n}");
	}
	
}
	
	

