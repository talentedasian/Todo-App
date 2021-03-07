package com.example.forum_4_stupid.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.forum_4_stupid.model.Users;
import com.example.forum_4_stupid.repository.UsersRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		try {
			Users user = usersRepository.findByUsername(username).get();
			return new User(username, user.getPassword(), user.isEnabled(), true, 
					true, true, getAuthoritiesAuthority("USER"));
			
		} catch (NoSuchElementException e) {
			
		}
		return null;
	}
	
	public Collection<? extends GrantedAuthority> getAuthoritiesAuthority (String role) {
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(new SimpleGrantedAuthority(role));
		
		return list;
		
	}

}
