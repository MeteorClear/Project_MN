package com.example.markdownnotes.controller;

import com.example.markdownnotes.model.User;
import com.example.markdownnotes.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

/**
 * 사용자 관련 API를 제공하는 컨트롤러 클래스.
 * 
 * 사용자 조회, 생성, 업데이트, 삭제 등의 기능을 처리.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	/**
	 * 사용자 서비스 객체.
	 * 
	 * 사용자 관련 로직을 처리.
	 */
	private final UserService userService;
	
	/**
	 * 모든 사용자를 조회하는 API.
	 * 
	 * @return 모든 사용자의 리스트를 반환.
	 */
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	/**
	 * Id 로 사용자를 조회하는 API.
	 * 
	 * @param id 조회할 사용자의 Id.
	 * @return 해당 Id 의 사용자를 반환, 사용자가 없을 경우 404 Not Found 반환.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Integer id) {
		Optional<User> user = userService.getUserById(id);
		return user.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	/**
	 * Email 로 사용자를 조회하는 API.
	 * 
	 * @param email 조회할 사용자의 Email.
	 * @return 해당 Email 사용자를 반환, 사용자가 없을 경우 404 Not Found 반환.
	 */
	@GetMapping("/email")
	public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
		Optional<User> user = userService.getUserByEmail(email);
		return user.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	/**
	 * 사용자를 생성하는 API.
	 * 
	 * @param user 생성할 사용자 정보.
	 * @return 생성된 사용자 정보를 반환, Email 이 중복된 경우 409 Conflict 반환.
	 */
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody User user) {
		try {
			User createdUser = userService.createUser(user);
			return ResponseEntity.ok(createdUser);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	/**
	 * 사용자를 삭제하는 API.
	 * 
	 * @param id 삭제할 사용자의 Id.
	 * @return 삭제 성공 시 204 No Content 반환.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * 사용자를 업데이트하는 API.
	 * 
	 * @param id 업데이트할 사용자의 Id.
	 * @param updatedUser 업데이트할 사용자 정보.
	 * @return 업데이트된 사용자 정보를 반환, 사용자가 없을 경우 404 Not Found 반환.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
		try {
			User user = userService.updateUser(id, updatedUser);
			return ResponseEntity.ok(user);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
