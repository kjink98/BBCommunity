package com.bbcommunity.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bbcommunity.domain.Role;
import com.bbcommunity.domain.User;
import com.bbcommunity.exception.DuplicateUserException;
import com.bbcommunity.repository.UserRepository;
@Service
public class RegisterUserService {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository repository;
	
	public RegisterUserService(PasswordEncoder passwordEncoder, UserRepository repository) {
		this.passwordEncoder = passwordEncoder;
		this.repository = repository;
	}
	
	public String register(String email, String password, String name, String gender, String nickname, Role role, LocalDateTime regdate) {
		// PasswordEncoder의 encode 함수가 불려 비밀번호를 암호화한 후 DB에 넣도록 세팅.
		User user = User.createUser(email, password, name, gender, nickname, role, regdate, passwordEncoder);
		validateDuplicateMember(user);
		repository.save(user);

		return user.getEmail();
	}
	
	private void validateDuplicateMember(User user) {
		repository.findByEmail(user.getEmail()).ifPresent(m -> {
			// throw new IllegalStateException("이미 존재하는 회원입니다.");
			throw new DuplicateUserException("이미 존재하는 회원입니다.");
		});
	}
}	
