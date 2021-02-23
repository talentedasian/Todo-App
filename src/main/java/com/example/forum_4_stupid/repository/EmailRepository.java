package com.example.forum_4_stupid.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.forum_4_stupid.model.Email;

@EnableJpaRepositories
public interface EmailRepository extends JpaRepository<Email, Integer> {

	
	List<Email> findByUser_Id(Integer user_ids);
}
