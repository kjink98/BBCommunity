package com.bbcommunity.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bbcommunity.dto.ChangeInfoRequestDto;
import com.bbcommunity.dto.ChangePasswordRequestDto;
import com.bbcommunity.entity.User;
import com.bbcommunity.repository.UserRepository;
import com.bbcommunity.role.Role;

@Service
public class UserService {
	private final UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
		this.userRepository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	public Optional<User> findByUserId(Long userId) {
		return userRepository.findById(userId);
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
			System.out.println("service " + userEmail);
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
			if (principal instanceof OAuth2User) {
				OAuth2User oauth2User = (OAuth2User) principal;
				OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) SecurityContextHolder
						.getContext().getAuthentication();
				String registrationId = authenticationToken.getAuthorizedClientRegistrationId();
				if ("google".equals(registrationId)) {
					return oauth2User.getAttribute("email");
				} else if ("kakao".equals(registrationId)) {
					Map<String, Object> kakaoAccount = (Map<String, Object>) oauth2User.getAttribute("kakao_account");
					return (String) kakaoAccount.get("email");
				} else {
					return ((OAuth2User) principal).getAttribute("email");
				}
			} else if (principal instanceof UserDetails) {
				// 다른 형태의 사용자 로그인을 처리하는 경우 UserDetails 타입을 받게 됨
				return ((UserDetails) principal).getUsername();
			}
		} catch (RuntimeException e) {
			// 예외가 발생한 경우 처리
			e.printStackTrace(); // 예외 처리 추가하기
		}
		return null;
	}

	@Transactional
	public void changePassword(ChangePasswordRequestDto requestDto) {
		// 로그인중인지 확인
		User user = getCurrentLoggedInMember();

		if (user == null || !passwordEncoder.matches(requestDto.getExPassword(), user.getPassword())) {
			throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
		}

		// 비밀번호 null 이거나 빈 문자열 여부 확인
		if (!StringUtils.hasText(requestDto.getExPassword()) || !StringUtils.hasText(requestDto.getNewPassword())
				|| !StringUtils.hasText(requestDto.getNewPasswordChk())) {
			throw new IllegalArgumentException("비밀번호는 null이거나 빈 문자열일 수 없습니다.");
		}

		// 새 비밀번호와 확인 비밀번호 일치 여부 확인
		if (!requestDto.getNewPassword().equals(requestDto.getNewPasswordChk())) {
			throw new IllegalArgumentException("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
		}

		// 새 비밀번호로 업데이트
		userRepository.updateUserPassword(user.getId(), passwordEncoder.encode(requestDto.getNewPassword()));
	}

	public void changeInfo(ChangeInfoRequestDto requestDto) {
		// 로그인중인지 확인
		User user = getCurrentLoggedInMember();

		userRepository.updateInfo(user.getId(), requestDto.getNewNickname());
	}

	/**
	 * 주어진 아이디와 비밀번호에 해당하는 사용자 정보를 데이터베이스에서 삭제하는 메소드
	 */
	public int resignUser(String email, String exPassword) {
		User user = userRepository.findByEmail(email).orElse(null);
		if (user == null || !passwordEncoder.matches(exPassword, user.getPassword())) {
			return 0;
		}

		return userRepository.resignUser(email);
	}

	/**
	 * 모든 사용자 정보를 데이터베이스에서 가져오는 메소드
	 */
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Transactional
	public void updateRoles(String email, Role role) {
		userRepository.updateRoles(email, role);
	}
}
