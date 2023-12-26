package com.bbcommunity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bbcommunity.entity.User;
import com.bbcommunity.repository.UserRepository;
@Service
public class UserService {
	private final UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
		this.userRepository = repository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User getCurrentLoggedInMember() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// Spring Security의 SecurityContextHolder를 사용하여 현재 로그인 중인 사용자의 Principal을 가져옴

		if (authentication == null || !authentication.isAuthenticated()) {
			// 사용자가 로그인하지 않은 경우 또는 인증되지 않은 경우
			return null;
		}

		try {
			// Principal에서 사용자의 이름(email)을 가져옴
			String userEmail = extractUserEmail(authentication.getPrincipal());
			// 이메일을 사용하여 Member 엔티티를 찾음
			return userRepository.findByEmail(userEmail).orElse(null);
		} catch (RuntimeException e) {
			// 예외가 발생한 경우 처리
			e.printStackTrace(); // 예외 처리 추가하기
			return null;
		}
	}
	
	private String extractUserEmail(Object principal) {
		try {
//			if (principal instanceof OAuth2User) {
//				// OAuth Login을 할 시 OAuth2User 타입을 받게 됨
//				return ((OAuth2User) principal).getAttribute("email");
//			} else
		 if (principal instanceof UserDetails) {
				// 다른 형태의 사용자 로그인을 처리하는 경우 UserDetails 타입을 받게 됨
				return ((UserDetails) principal).getUsername();
			} else {
				// 기타
				return null;
			}
		} catch (RuntimeException e) {
			// 예외가 발생한 경우 처리
			e.printStackTrace(); // 예외 처리 추가하기
			return null;
		}
	}
}
