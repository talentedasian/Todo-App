package com.example.forum_4_stupid.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.example.forum_4_stupid.LoggerClass;

@Component
public class LoginCustomAuthenticationFailureHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		LoggerClass.getLogger(LoginCustomAuthenticationFailureHandler.class).log(Level.INFO, "Authentication failed");
	}

}
