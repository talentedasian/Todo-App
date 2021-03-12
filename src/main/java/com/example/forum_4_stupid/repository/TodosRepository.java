package com.example.forum_4_stupid.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.forum_4_stupid.model.Todos;

public interface TodosRepository extends JpaRepository<Todos, Integer>{

	 List<Todos> findByUser_Id(Integer user_ids);
	 
	 List<Todos> findByUser_Username(String username);
}
