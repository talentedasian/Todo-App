package com.example.forum_4_stupid.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.forum_4_stupid.exceptions.AccessIsDeniedException;
import com.example.forum_4_stupid.exceptions.JwtNotFoundException;

@RestController
@RequestMapping("/error")
public class GlobalExceptionController {
	
	@GetMapping("/jwtNotFound")
	public void jwtNotFoundException () {
		throw new AccessIsDeniedException("Access Denied! User Accessed protected resouce from another user", 
				new JwtNotFoundException("User accessed resource without JWT."));
	}
}
