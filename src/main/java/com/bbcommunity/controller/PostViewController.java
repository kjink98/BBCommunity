package com.bbcommunity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bbcommunity.entity.Board;
import com.bbcommunity.entity.Posts;
import com.bbcommunity.service.BoardService;
import com.bbcommunity.service.PostService;

@Controller
@RequestMapping("/post")
public class PostViewController {
	@Autowired
    private PostService postService;
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/all")
	public String allPosts(Model model) {
	    List<Posts> posts = postService.findAll();
	    model.addAttribute("posts", posts);
	    return "post/allPostsView";
	}

	@GetMapping("/free")
	public String freeBoard(Model model) {
		Board board = boardService.getBoardByName("자유게시판");
	    model.addAttribute("board", board);
	    
		return "post/freeBoardView";
	}

	@GetMapping("/notice")
	public String noticeBoard() {
		// 공지사항 게시판 로직 처리
		return "post/noticeBoardView";
	}
	
	@GetMapping("/write")
	public String writePost() {
	    // 'post/writePost' 뷰로 이동
	    return "post/writePost";
	}
}
