package com.example.forum_4_stupid.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.forum_4_stupid.model.Email;

public interface EmailRepository extends JpaRepository<Email, Integer> {

}