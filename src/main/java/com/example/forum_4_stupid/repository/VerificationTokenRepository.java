package com.example.forum_4_stupid.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.forum_4_stupid.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{

}
