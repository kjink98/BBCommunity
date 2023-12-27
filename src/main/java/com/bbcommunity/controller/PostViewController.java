package com.bbcommunity.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String allPosts(Model model, @RequestParam(defaultValue = "0") int page) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("postRegdate").descending());
		Page<Posts> postsPage = postService.findAll(pageable);
		
        model.addAttribute("posts", postsPage.getContent());
        model.addAttribute("postsPage", postsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postsPage.getTotalPages());
        
		return "post/allPostsView";
	}

	@GetMapping("/free")
	public String freeBoard(Model model, @RequestParam(defaultValue = "0") int page) {
	    Board board = boardService.getBoardById((long) 1);
	    Pageable pageable = PageRequest.of(page, 10, Sort.by("postRegdate").descending());
	    Page<Posts> postsPage = postService.findByBoardBoardId(board.getBoardId(), pageable);
		
	    model.addAttribute("posts", postsPage.getContent());
	    model.addAttribute("postsPage", postsPage);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", postsPage.getTotalPages());

		return "post/freeBoardView";
	}

	@GetMapping("/notice")
	public String noticeBoard(Model model, @RequestParam(defaultValue = "0") int page) {
		Board board = boardService.getBoardById((long) 2);
	    Pageable pageable = PageRequest.of(page, 10, Sort.by("postRegdate").descending());
	    Page<Posts> postsPage = postService.findByBoardBoardId(board.getBoardId(), pageable);
		
	    model.addAttribute("posts", postsPage.getContent());
	    model.addAttribute("postsPage", postsPage);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", postsPage.getTotalPages());


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
	        postForm.setUser(user);
	        postService.save(postForm);	
	    return "redirect:/post/all";
	}
	
	@GetMapping("/detail/{id}")
	public String postDetail(@PathVariable Long id, Model model) {
		Optional<Posts> post = postService.findByPostId(id);
		if (post.isPresent()) {
	        // 게시글이 존재하면 해당 게시글 정보를 모델에 추가하여 상세 페이지에 전달
	        model.addAttribute("post", post.get());
	        return "post/postDetailView";
	    } else {
	        // 게시글이 존재하지 않으면 예외 처리 또는 적절한 조치
	        return "errorPage"; // 예시로 에러 페이지를 보여줌
	    }
	}
}
