package com.example.forum_4_stupid.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.forum_4_stupid.model.Todos;

public interface TodosRepository extends JpaRepository<Todos, Integer>{

	 Optional<Todos> findByUser_Id(Integer user_ids);
	 
	 Optional<Todos> findByUser_Username(String username);
}
