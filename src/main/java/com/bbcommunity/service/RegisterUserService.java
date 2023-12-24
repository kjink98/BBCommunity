package com.bbcommunity.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bbcommunity.domain.User;
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
		if (!password.equals(passwordCheck)) {
	        throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
	    }
	    LocalDateTime regdate = LocalDateTime.now();
	    Role role = Role.USER;
	    
	    User user = User.createUser(email, password, name, gender, nickname, role, regdate, passwordEncoder);

	    validateDuplicateMember(user);
	    userRepository.save(user);
	    
	    return user;
	}


	private void validateDuplicateMember(User user) {
		userRepository.findByEmail(user.getEmail()).ifPresent(m -> {
			System.out.println("error : " + m);
		});
	}
}
