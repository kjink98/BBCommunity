package com.bbcommunity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bbcommunity.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserApiController {
	public final UserRepository userRepository;
	
	public UserApiController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
}
