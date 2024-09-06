package com.example.markdownnotes.service;

import com.example.markdownnotes.model.User;
import com.example.markdownnotes.repository.UserRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	
	// 모든 사용자 조회
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	// 사용자 Id 로 조회
	public Optional<User> getUserById(Integer id) {
		return userRepository.findById(id);
	}
	
	// 사용자 email 로 조회
	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	// 사용자 생성
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	// 사용자 삭제
	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}
}
