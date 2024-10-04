package com.example.markdownnotes.service;

import com.example.markdownnotes.model.User;
import com.example.markdownnotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

/**
 * 사용자 인증 서비스 클래스.
 * 
 * Spring Security 에서 사용자 인증을 처리하는 데 사용.
 * 이메일로 사용자를 조회하여 인증.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService  {

	/**
	 * 사용자 레퍼지토리 인터페이스.
	 * 
	 * DB와 상호작용하여 사용자 정보 관리.
	 */
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 이메일로 사용자 정보 확인
	 * 
	 * 주어진 이메일을 통해 사용자를 조회.
	 * 해당 사용자의 인증 정보를 반환.
	 * 
	 * @param username 사용자명 (로그인 Id 로 사용된 이메일)
	 * @return UserDetails 인증을 위한 사용자 정보 객체
	 * @throws UsernameNotFoundException 이메일에 해당하는 사용자를 찾을 수 없을 경우 예외 처리
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("[ERROR]User not found, email: " + username));
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}
}
