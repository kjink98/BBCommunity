package com.bbcommunity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bbcommunity.dto.UserRegisterDto;
import com.bbcommunity.entity.User;
import com.bbcommunity.service.RegisterUserService;

@Controller
@RequestMapping("/user")
public class AuthorizationController {
	private final RegisterUserService registerUserService;

	public AuthorizationController(RegisterUserService registerUserService) {
		this.registerUserService = registerUserService;
	}

	@PostMapping("/register")
	public String register(@ModelAttribute UserRegisterDto userRegisterDto, Model model) {
	    try {
	        User user = registerUserService.register(
	            userRegisterDto.getEmail(),
	            userRegisterDto.getPassword(),
	            userRegisterDto.getPasswordCheck(),
	            userRegisterDto.getName(),
	            userRegisterDto.getGender(),
	            userRegisterDto.getNickname()
	        );

	        model.addAttribute("message", "회원가입이 완료되었습니다.");
	        return "user/registerSuccess";  // Thymeleaf 템플릿 이름
	    } catch (Exception e) {
	        model.addAttribute("message", e.getMessage());
	        return "registerError";  // Thymeleaf 템플릿 이름
	    }
	}

}
