package com.bbcommunity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bbcommunity.domain.User;

@Controller
@RequestMapping("/user")
public class UserViewController {
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "user/register";
	}
}
