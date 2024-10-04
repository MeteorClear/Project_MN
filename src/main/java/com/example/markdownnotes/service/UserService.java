package com.example.markdownnotes.service;

import com.example.markdownnotes.model.User;
import com.example.markdownnotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

/**
 * 사용자 관리 서비스 클래스.
 * 
 * 사용자 생성, 조회, 업데이트, 삭제와 같은 사용자 관리 기능 당담.
 */
@Service
@RequiredArgsConstructor
public class UserService {
	
	/**
	 * 사용자 레퍼지토리 인터페이스.
	 * 
	 * DB와 상호작용하여 사용자 정보 관리.
	 */
	private final UserRepository userRepository;
	
	/**
	 * 비밀번호 인코더.
	 * 
	 * 사용자의 비밀번호를 해시 암호화하여 사용.
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * 모든 사용자 조회.
	 * 
	 * @return List<User> 전체 사용자 목록
	 */
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	/**
	 * 사용자 id 로 조회.
	 * 
	 * @param id 조회할 사용자의 Id
	 * @return Optional<User> ID에 해당하는 사용자의 정보
	 */
	public Optional<User> getUserById(Integer id) {
		return userRepository.findById(id);
	}
	
	/**
	 * 사용자 email 로 조회.
	 * 
	 * @param email 조회할 사용자의 Email
	 * @return Optional<User> 이메일에 해당하는 사용자의 정보
	 */
	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	/**
	 * 사용자 생성.
	 * 
	 * @param user 생성할 사용자 정보
	 * @return User 생성된 사용자 정보
	 * @throws IllegalArgumentException 이메일이 중복된 경우 예외 처리
	 */
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
	
	/**
	 * 사용자 삭제.
	 * 
	 * @param id 삭제할 사용자의 Id
	 */
	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}
	
	/**
	 * 사용자 업데이트.
	 * 
	 * @param id 업데이트할 사용자의 Id
	 * @param updatedUser 업데이트할 사용자 정보
	 * @return User 업데이트된 사용자 정보
	 * @throws RuntimeException Id에 해당하는 사용자가 없을 경우 예외 처리
	 */
	public User updateUser(Integer id, User updatedUser) {
		return userRepository.findById(id).map(user -> {
			user.setEmail(updatedUser.getEmail());
			user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
			user.setUsername(updatedUser.getUsername());
			return userRepository.save(user);
		}).orElseThrow(() -> new RuntimeException("[ERROR]User not found, id: " + id));
	}
}
