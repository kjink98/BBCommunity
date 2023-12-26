package com.bbcommunity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bbcommunity.dto.PostForm;
import com.bbcommunity.entity.Board;
import com.bbcommunity.entity.Posts;
import com.bbcommunity.entity.User;
import com.bbcommunity.service.BoardService;
import com.bbcommunity.service.PostService;
import com.bbcommunity.service.UserService;

@Controller
@RequestMapping("/post")
public class PostViewController {
	@Autowired
	private PostService postService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private UserService userService;

	@GetMapping("/all")
	public String allPosts(Model model) {
		List<Posts> posts = postService.findAll();
		System.out.println("postservice.findaAll"+postService.findAll());
		model.addAttribute("posts", posts);
		return "post/allPostsView";
	}

	@GetMapping("/free")
	public String freeBoard(Model model) {
		Board board = boardService.getBoardById((long) 1);
		List<Posts> posts = postService.findByBoardBoardId(board.getBoardId());
		model.addAttribute("posts", posts);
		return "post/freeBoardView";
	}

	@GetMapping("/notice")
	public String noticeBoard(Model model) {
		Board board = boardService.getBoardById((long) 2);
		List<Posts> posts = postService.findByBoardBoardId(board.getBoardId());
		model.addAttribute("board", board);

		return "post/noticeBoardView";
	}
	
	
	@GetMapping("/write")
	public String writePost() {
		// 'post/writePost' 뷰로 이동
		return "post/writePost";
	}

	@PostMapping("/write")
	public String savePost(@ModelAttribute PostForm postForm) {
		User user = userService.getCurrentLoggedInMember();
		System.out.println("contrller user : "+user);
		if (user != null) {
	        postForm.setUser(user);
	        System.out.println("postForm before save: "+ postForm.getUser());
	        postService.save(postForm);
	        System.out.println("postForm after save: "+ postForm.getUser());
	    } else {
	        System.out.println("로그인 되지 않음");
	    }
	    return "redirect:/post/all";
	}
}
