package com.example.forum_4_stupid.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public class LoginFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;

		if (res.getStatus() == 403) {
			throw new AccessDeniedException("Login Credentials Do Not Match on any of Our Records");
		}
		
		chain.doFilter(request, response);
	}

}
