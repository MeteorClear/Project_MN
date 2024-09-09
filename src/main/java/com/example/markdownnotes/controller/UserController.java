package com.example.markdownnotes.controller;

import com.example.markdownnotes.model.User;
import com.example.markdownnotes.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	// 모든 사용자 조회
	
	// 사용자 Id 로 조회
	
	// 사용자 email 로 조회
	
	// 사용자 생성
	
	// 사용자 삭제
	
	// 사용자 업데이트
}
