package com.example.markdownnotes.repository;

import com.example.markdownnotes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * 사용자(User) 엔티티에 대한 데이터 접근 레이어 클래스.
 * 
 * JpaRepository를 상속받아 CRUD 기능을 제공.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	/**
	 * 이메일을 통해 사용자를 조회
	 * 
	 * @param email 사용자의 이메일
	 * @return 주어진 이메일에 해당하는 사용자
	 */
	Optional<User> findByEmail(String email);
	
	/**
	 * 닉네임을 통해 사용자를 조회
	 * 
	 * @param username 사용자의 닉네임
	 * @return 주어진 닉네임에 해당하는 사용자
	 */
	Optional<User> findByUsername(String username);
}
