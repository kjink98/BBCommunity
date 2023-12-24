package com.bbcommunity.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bbcommunity.domain.User;
import com.bbcommunity.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserApiController {
	public final UserRepository userRepository;
	
	public UserApiController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Optional<User> user = userRepository.findByEmail(email);
        System.out.println("야");
        System.out.println(user.isPresent());
        if (!user.isPresent()) {
            return "redirect:/user/login";
        }

        User actualUser  = user.get();
        
        System.out.println("actualUser" + actualUser);
        
        if (!actualUser.getPassword().equals(password)) {
            return "redirect:/user/login";
        }
        
        System.out.println("아이디" + actualUser.getEmail());
        System.out.println("로그인 성공");

        session.setAttribute("user", actualUser);
        return "redirect:/";
    }
}
