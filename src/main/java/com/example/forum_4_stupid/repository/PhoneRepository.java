package com.example.forum_4_stupid.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.forum_4_stupid.model.PhoneNumber;

public interface PhoneRepository extends JpaRepository<PhoneNumber, Integer> {

	
	List<PhoneNumber> findByUser_Id(Integer user_ids);
	
	List<PhoneNumber> findByUser_Username(String username);
	
}
