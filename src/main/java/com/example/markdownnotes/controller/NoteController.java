package com.example.markdownnotes.controller;

import com.example.markdownnotes.model.Note;
import com.example.markdownnotes.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
	private final NoteService noteService;
	
	// 모든 메모 조회
	
	// 메모 id 로 조회
	
	// 특정 사용자의 메모 목록 조회
	
	// 메모 생성
	
	// 메모 삭제
	
	// 메모 업데이트
}
