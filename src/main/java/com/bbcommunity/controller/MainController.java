package com.bbcommunity.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {
        // 페이지 번호와 페이지 크기를 이용하여 Pageable 객체를 생성
        PageRequest pageable = PageRequest.of(page, 10, Sort.by("postRegdate").descending()); // 페이지 크기는 10으로 설정 (원하는 크기로 변경 가능)

        // 페이징된 데이터 가져오기
        Page<Posts> postsPage = postService.findAll(pageable);
        
        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("postsPage", postsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postsPage.getTotalPages());

        return "index";
    }
}	
