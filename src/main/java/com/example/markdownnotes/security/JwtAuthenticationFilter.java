package com.example.markdownnotes.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * JWT 인증 필터 클래스.
 * 
 * 해당 필터는 HTTP 요청이 들어올 때마다 한 번 실행되며, 요청의 헤더에 포함된 JWT 토큰을 확인.
 * 인증된 사용자인 경우 SecurityContext에 인증 정보를 설정.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	/**
	 * JWT 토큰 유틸리티 클래스.
	 * 
	 * 토큰 생성, 유효성 검사, 사용자명 추출 등의 기능을 제공.
	 */
	private final JwtTokenUtil jwtTokenUtil;
	
	/**
	 * JwtAuthenticationFilter 생성자
	 * 
	 * @param jwtTokenUtil JWT 토큰 유틸리티 클래스
	 */
	public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	/**
	 * HTTP 요청을 필터링.
	 * 
	 * Authorization 헤더에서 JWT 토큰을 추출하고, 유효한 토큰이면 SecurityContext에 인증 정보를 설정.
	 * 
	 * @param request HTTP 요청
	 * @param response HTTP 응답
	 * @param chain 필터 체인
	 * @throws ServletException 요청 처리 중 예외 처리
	 * @throws IOException 입출력 처리 중 예외 처리
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain chain) 
											throws ServletException, IOException {
		
		// Authorization 헤더에서 토큰 추출
		String header = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		// 헤더가 있으면서 'Bearer '로 시작하면 토큰 추출
		if (header != null && header.startsWith("Bearer")) {
			token = header.substring(7);								// 'Bearer ' 이후의 토큰 부분 추출
			username = jwtTokenUtil.getUsernameFromToken(token);		// 토큰에서 사용자명 추출
		}
		
		// 사용자명이 있으면서 SecurityContext에 인증정보가 없으면서 토큰이 유효하면, 인증정보 설정
		if (username != null &&
				SecurityContextHolder.getContext().getAuthentication() == null &&
				jwtTokenUtil.validateToken(token, username)) {
			
			// 인증 객체 생성, 사용자명을 설정하고 권한 정보는 비워둠
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					username, 
					null, 
					new ArrayList<>());
			
			// 요청에 대한 세부 정보 설정
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			// SecurityContext에 인증 정보 설정
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		// 필터 체인, 요청을 다음 필터로 전달
		chain.doFilter(request, response);
	}
}
