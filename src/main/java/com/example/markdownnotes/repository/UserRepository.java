package com.example.markdownnotes.repository;

import com.example.markdownnotes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	// 이메일로 사용자 조회 - 로그인 용
	Optional<User> findByEmail(String email);
	
	// 닉네임으로 사용자 조회 - 미정
	Optional<User> findByUsername(String username);
}
