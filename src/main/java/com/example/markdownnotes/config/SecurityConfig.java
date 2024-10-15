package com.example.markdownnotes.config;

import com.example.markdownnotes.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 설정 클래스.
 * 
 * 애플리케이션 보안 설정, 필터 체인 구성, 사용자 인증, 권한 부여, 암호화 등의 기능을 담당.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	/**
	 * JWT 인증 필터.
	 * 
	 * 해당 필터는 모든 요청에서 JWT 토큰을 확인.
	 * 인증된 사용자라면 SecurityContextHolder에 인증 정보를 설정.
	 */
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	/**
	 * SecurityConfig 생성자.
	 * 
	 * JWT 필터를 주입하여 생성.
	 * 
	 * @param jwtAuthenticationFilter JWT 인증 필터
	 */
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}
	
	/**
	 * 보안 필터 체인 설정.
	 * 
	 * 접근 제어, 세션 정책, HTTP 요청에 대한 보안설정을 정의.
	 * 
	 * @param http HttpSecurity 객체, 보안 설정을 적용할 수 있는 클래스
	 * @return SecurityFilterChain 보안 필터 체인 객체
	 * @throws Exception 예외 처리
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable()) 				// CSRF 보호 비활성화
			.authorizeHttpRequests(auth -> auth			// 요청 경로별로 접근 권한을 설정
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.requestMatchers(HttpMethod.DELETE, "/api/users/{id}").authenticated()		// 사용자 삭제 경로
				.requestMatchers(HttpMethod.PUT, "/api/users/{id}").authenticated()			// 사용자 업데이트 경로
				.requestMatchers(						// 인증이 필요 없는 경로
						"/api/auth/**",					// 로그인 경로
						"/api/users/**",				// 사용자 조회, 회원가입 경로
						"/swagger-ui.html",				// Swagger UI 경로
						"/v3/api-docs/**",				// OpenAPI 문서 경로
						"/swagger-ui/**",				// Swagger 리소스 경로
						"/swagger-resources/**",		// Swagger 리소스 경로
						"/webjars/**",					// 웹 자바스크립트 라이브러리 관련 경로
						"/error"						// 에러 페이지 경로
						).permitAll()
				.anyRequest().authenticated()) 			// 나머지 경로는 인증 필요
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); 					// 세션을 사용안함, JWT 토큰 기반 인증 처리
	
		// JWT 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	
		return http.build();
	}
	
	/**
	 * 인증 관리자 설정.
	 * 
	 * AuthenticationConfiguration에서 AuthenticationManager를 가져와 등록. 
	 * AuthenticationManager 클래스는 인증을 처리하는 클래스.
	 * 
	 * @param authenticationConfiguration AuthenticationConfiguration 객체
	 * @return AuthenticationManager 인증 관리자 객체
	 * @throws Exception 예외 처리
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	/**
	 * 비밀번호 인코더 설정.
	 * 
	 * BCryptPasswordEncoder를 사용하여 비밀번호를 인코딩.
	 * BCrypt는 강력한 해싱 알고리즘.
	 * 
	 * @return PasswordEncoder 비밀번호 인코더 객체
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
