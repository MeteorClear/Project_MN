package com.example.markdownnotes.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenUtil jwtTokenUtil;
	
	public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	// OncePerRequestFilter
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response, 
									FilterChain chain) 
											throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		// 헤더가 있으면서 'Bearer '로 시작하면 토큰 추출
		if (header != null && header.startsWith("Bearer")) {
			token = header.substring(7);
			username = jwtTokenUtil.getUsernameFromToken(token);
		}
		
		// 사용자명이 있으면서 SecurityContext에 인증정보가 없으면서 토큰이 유효하면, 인증정보 설정
		if (username != null &&
				SecurityContextHolder.getContext().getAuthentication() == null &&
				jwtTokenUtil.validateToken(token, username)) {
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					username, 
					null, 
					new ArrayList<>());
			
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		// 필터 체인, 요청을 다음 필터로 전달
		chain.doFilter(request, response);
	}
}
