package com.example.markdownnotes.controller;

import com.example.markdownnotes.model.JwtRequest;
import com.example.markdownnotes.model.JwtResponse;
import com.example.markdownnotes.security.JwtTokenUtil;
import com.example.markdownnotes.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * JWT 인증 관련 API를 제공하는 컨트롤러 클래스.
 * 
 * 로그인 시 JWT 토큰 발급, 사용자 인증 등의 기능을 처리.
 */
@RestController
@RequestMapping("/api/auth")
public class JwtAuthenticationController {
	
	/**
	 * Spring Security의 인증 관리자.
	 * 
	 * 사용자 인증을 처리.
	 */
	@Autowired
	private AuthenticationManager authenticationManager;
	
	/**
	 * JWT 토큰 유틸리티 클래스.
	 * 
	 * JWT 토큰 생성 및 검증.
	 */
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	/**
	 * 커스텀 사용자 서비스.
	 * 
	 * 사용자 정보를 로드하는 서비스.
	 */
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	/**
	 * 사용자 인증 후 JWT 토큰을 발급하는 API. (로그인)
	 * 
	 * @param authenticationRequest 사용자명과 비밀번호를 담은 요청 객체
	 * @return 발급된 JWT 토큰을 포함한 응답
	 * @throws Exception 인증 실패 시 예외 처리
	 */
	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		
		final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		
		final String token = jwtTokenUtil.generateToken(userDetails.getUsername());
		
		return ResponseEntity.ok(new JwtResponse(token));
	}

	/**
	 * 사용자 인증을 처리.
	 * 
	 * @param email 사용자 이메일
	 * @param password 사용자 비밀번호
	 * @throws Exception 인증 실패 시 예외 발생
	 */
	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (BadCredentialsException e) {
			throw new Exception("[ERROR]Invalid Credentials", e);
		}
	}
}
