package com.bbcommunity.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bbcommunity.entity.Posts;
import com.bbcommunity.service.BoardService;
import com.bbcommunity.service.PostService;
import com.bbcommunity.service.UserService;

@Controller
public class MainController {
	@Autowired
	private PostService postService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private UserService userService;
	@GetMapping("/")
	public String index(Model model) {
		List<Posts> posts = postService.findAll();
		model.addAttribute("posts", posts);
		return "index";
	}
}	
