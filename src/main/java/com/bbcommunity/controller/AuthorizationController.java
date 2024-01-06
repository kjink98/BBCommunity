package com.bbcommunity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bbcommunity.dto.UserRegisterDto;
import com.bbcommunity.entity.User;
import com.bbcommunity.service.RegisterUserService;
/*
* 이 컨트롤러 클래스는 사용자 인증과 관련된 요청을 처리합니다.
*/
@Controller
@RequestMapping("/user")
public class AuthorizationController {
	private final RegisterUserService registerUserService;
	/*
	* 의존성 주입을 통해 RegisterUserService를 가져옵니다.
	* 이 서비스는 사용자 등록과 관련된 로직을 처리합니다.
	*/
	public AuthorizationController(RegisterUserService registerUserService) {
		this.registerUserService = registerUserService;
	}
	/*
	* 이 메서드는 사용자 등록 요청을 처리하며, 이를 위해 UserRegisterDto 객체를 파라미터로 받습니다.
	* Model 객체를 통해 뷰에 데이터를 전달합니다.
	*/
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
	        return "error/registerError";
	    }
	}

}
