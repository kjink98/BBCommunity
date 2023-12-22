package com.bbcommunity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bbcommunity.domain.User;
import com.bbcommunity.service.RegisterUserService;


@RestController
@RequestMapping("/user")
public class AuthorizationController {
	private final RegisterUserService registerUserService;
	
	public AuthorizationController(RegisterUserService registerUserService) {
        this.registerUserService = registerUserService;
    }
	
	@PostMapping("/regisger")
    public ResponseEntity<?> register(@ModelAttribute User user) {
//    	if (!user.getPassword().equals(user.getPasswordCheck())) {
//            return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
//        }
        try {
            registerUserService.register(
                user.getEmail(),
                user.getPassword(),
                user.getName(),	
                user.getNickname(),
                user.getGender(),
                user.getRole(),
                user.getRegdate()
            );
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
