package com.example.markdownnotes.repository;

import com.example.markdownnotes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	// 기늧 추가
}
