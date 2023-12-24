package com.bbcommunity.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.bbcommunity.domain.User;
import com.bbcommunity.repository.UserRepository;
import com.bbcommunity.role.Role;

import jakarta.transaction.Transactional;

@Service
public class RegisterUserService {
	private final UserRepository userRepository;

	public RegisterUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public Long register(String email, String password, String name, String gender, String nickname, Role role,
			LocalDateTime regdate) {
		if (regdate == null) {
	        // 'regdate' 파라미터가 'null'이라면 현재 시간을 설정
	        regdate = LocalDateTime.now();
	    }
		
		User user = User.createUser(email, password, name, gender, nickname, role, regdate);

		validateDuplicateMember(user);

		userRepository.save(user);
		return user.getId();
	}

	private void validateDuplicateMember(User user) {
		userRepository.findByEmail(user.getEmail()).ifPresent(m -> {
			System.out.println("error : " + m);
		});
	}
}
