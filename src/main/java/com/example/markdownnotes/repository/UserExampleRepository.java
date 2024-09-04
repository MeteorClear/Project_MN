package com.example.markdownnotes.repository;

import com.example.markdownnotes.model.UserExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Example JPA Repository
 * Maybe, Not to used
 */

@Repository
public interface UserExampleRepository extends JpaRepository<UserExample, Long> {
	UserExample findByEmail(String email);
}
