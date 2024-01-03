package com.bbcommunity.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bbcommunity.entity.User;
import com.bbcommunity.repository.UserRepository;
import com.bbcommunity.role.Role;

import jakarta.transaction.Transactional;

@Service
public class RegisterUserService {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public RegisterUserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	@Transactional
	public User register(String email, String password, String passwordCheck, String name, String gender, String nickname) {
	    LocalDateTime regdate = LocalDateTime.now();
	    Role role = Role.USER;
	    
	    User user = User.createUser(email, password, name, gender, nickname, role, regdate, passwordEncoder);

	    validateDuplicateMember(user);
	    userRepository.save(user);
	    
	    return user;
	}


	private void validateDuplicateMember(User user) {
	    userRepository.findByEmail(user.getEmail()).ifPresent(m -> {
	        throw new IllegalStateException("이미 가입된 회원입니다.");
	    });
	}
}
