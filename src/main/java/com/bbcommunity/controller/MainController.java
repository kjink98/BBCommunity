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
/*
 * MainController 클래스는 게시판의 메인 페이지와 관련된 요청을 처리합니다.
 */
@Controller
public class MainController {
	@Autowired
	private PostService postService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private UserService userService;
	
	/*
	 * 메인 페이지를 표시하는 메서드입니다.
	 * @GetMapping 어노테이션을 사용하여 "/" 경로로 들어오는 GET 요청을 처리합니다.
	 * 페이지 번호를 파라미터로 받아 해당 페이지의 게시글을 페이징하여 표시합니다.
	 */
	@GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {
		// 페이지 번호와 페이지 크기를 이용하여 Pageable 객체를 생성
		// 페이지 크기는 5로 설정하고, 게시글 등록일(postRegdate)을 기준으로 내림차순 정렬합니다.
        PageRequest pageable = PageRequest.of(page, 5, Sort.by("postRegdate").descending()); // 페이지 크기는 10으로 설정 (원하는 크기로 변경 가능)

        // 페이징된 데이터 가져오기
        Page<Posts> postsPage = postService.findAll(pageable);
        
        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("postsPage", postsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postsPage.getTotalPages());

        return "index";
    }
}	
