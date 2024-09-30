package com.example.markdownnotes.service;

import com.example.markdownnotes.model.User;
import com.example.markdownnotes.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
		// 이메일 중복 확인
		Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
		if (existingUser.isPresent()) {
			throw new IllegalArgumentException("[ERROR]User already exists");
		}
		
		// 비밀번호 인코딩
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	// 사용자 삭제
	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}
	
	// 사용자 업데이트
	public User updateUser(Integer id, User updatedUser) {
		return userRepository.findById(id).map(user -> {
			user.setEmail(updatedUser.getEmail());
			user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
			user.setUsername(updatedUser.getUsername());
			return userRepository.save(user);
		}).orElseThrow(() -> new RuntimeException("[ERROR]User not found, id: " + id));
	}
}
