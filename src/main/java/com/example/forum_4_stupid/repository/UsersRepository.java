package com.example.forum_4_stupid.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.forum_4_stupid.model.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

	public Optional<Users> findByUsername (String username);
	
	
}

