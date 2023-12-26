package com.bbcommunity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbcommunity.dto.UserRegisterDto;
import com.bbcommunity.entity.User;
import com.bbcommunity.service.RegisterUserService;

@RestController
@RequestMapping("/user")
public class AuthorizationController {
	private final RegisterUserService registerUserService;

	public AuthorizationController(RegisterUserService registerUserService) {
		this.registerUserService = registerUserService;
	}

	@PostMapping("/register")
    public ResponseEntity<?> register(@ModelAttribute UserRegisterDto userRegisterDto) {
        try {
				User user = registerUserService.register(
		            userRegisterDto.getEmail(),
		            userRegisterDto.getPassword(),
		            userRegisterDto.getPasswordCheck(),
		            userRegisterDto.getName(),
		            userRegisterDto.getGender(),
		            userRegisterDto.getNickname()
			        );
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
