package com.example.markdownnotes.controller;

import com.example.markdownnotes.model.User;
import com.example.markdownnotes.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	// 모든 사용자 조회
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	// 사용자 Id 로 조회
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Integer id) {
		Optional<User> user = userService.getUserById(id);
		return user.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	// 사용자 email 로 조회
	@GetMapping("/email")
	public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
		Optional<User> user = userService.getUserByEmail(email);
		return user.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	// 사용자 생성
	
	// 사용자 삭제
	
	// 사용자 업데이트
}
