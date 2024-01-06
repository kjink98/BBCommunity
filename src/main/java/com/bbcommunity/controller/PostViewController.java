package com.bbcommunity.controller;

import java.util.List;
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
import com.bbcommunity.entity.Comment;
import com.bbcommunity.entity.Posts;
import com.bbcommunity.entity.User;
import com.bbcommunity.role.Role;
import com.bbcommunity.service.BoardService;
import com.bbcommunity.service.CommentService;
import com.bbcommunity.service.PostService;
import com.bbcommunity.service.UserService;
/*
 * PostViewController 클래스는 게시글 관련 요청을 처리합니다.
 */
@Controller
@RequestMapping("/post")
public class PostViewController {
	@Autowired
	private PostService postService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private UserService userService;

	private final CommentService commentService;

	@Autowired
	public PostViewController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	/*
	 * 모든 게시물 가져오기
	 */
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
	
	/*
	 * 자유 게시판의 게시물 가져오기
	 */
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
	
	/*
	 * 공지사항 게시판의 게시물 가져오기
	 */
	@GetMapping("/notice")
	public String noticeBoard(Model model, @RequestParam(defaultValue = "0") int page) {
		User loggedInUser = userService.getCurrentLoggedInMember();
		
		boolean isAdmin = loggedInUser.getRole() == Role.ADMIN;
		model.addAttribute("isAdmin", isAdmin);
		
		Board board = boardService.getBoardById((long) 2);
		Pageable pageable = PageRequest.of(page, 10, Sort.by("postRegdate").descending());
		Page<Posts> postsPage = postService.findByBoardBoardId(board.getBoardId(), pageable);

		model.addAttribute("posts", postsPage.getContent());
		model.addAttribute("postsPage", postsPage);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", postsPage.getTotalPages());

		return "post/noticeBoardView";
	}

	/*
	 * 자유 게시판에 글 작성 폼 보기
	 */
	@GetMapping("/write/free")
	public String writeFreePost() {
		return "post/writeFreePost";
	}

	/*
	 * 곻지사항 게시판에 글 작성 폼 보기
	 */
	@GetMapping("/write/notice")
	public String writeNoticePost(Model model) {
		return "post/writeNoticePost";
	}

	/*
	 * 자유 게시판에 글 저장하기
	 */
	@PostMapping("/write/free")
	public String saveFreePost(@ModelAttribute PostForm postForm) {
		User user = userService.getCurrentLoggedInMember();
		postForm.setUser(user);
		postForm.setBoard(boardService.getBoardById(1L)); // 자유 게시판 ID로 설정
		postService.save(postForm);
		return "redirect:/post/free";
	}

	/*
	 * 공지사항 게시판에 글 저장하기
	 */
	@PostMapping("/write/notice")
	public String saveNoticePost(@ModelAttribute PostForm postForm, Model model) {
		User loggedInUser = userService.getCurrentLoggedInMember();
		// 로그인한 사용자가 관리자인지 확인
	    if (loggedInUser.getRole() != Role.ADMIN) {
	        // 관리자가 아니라면 에러 메시지를 담고, 글쓰기 페이지로 리다이렉트
	        return "redirect:/post/write/notice";
	    }
		postForm.setUser(loggedInUser);
		postForm.setBoard(boardService.getBoardById(2L)); // 공지사항 게시판 ID로 설정
		postService.save(postForm);
		return "redirect:/post/notice";
	}

	/*
	 * 게시물 상세 정보 보기
	 */
	@GetMapping("/detail/{id}")
	public String postDetail(@PathVariable Long id, Model model) {
		Optional<Posts> post = postService.findByPostId(id);
		if (post.isPresent()) {
			// 게시글이 존재하면 해당 게시글 정보를 모델에 추가하여 상세 페이지에 전달
			model.addAttribute("post", post.get());

			// 해당 게시글의 댓글 목록을 가져와 모델에 추가
			List<Comment> comments = commentService.getCommentsByPost(post.get());
			model.addAttribute("comments", comments);

			// 새 댓글을 생성하기 위한 빈 Comment 객체도 모델에 추가
			model.addAttribute("newComment", new Comment());

			return "post/postDetailView";
		} else {
			// 게시글이 존재하지 않으면 예외 처리 또는 적절한 조치
			return "error/deleteErrorView"; // 예시로 에러 페이지를 보여줌
		}
	}

	/*
	 * 게시물 수정 폼 보기
	 */
	@GetMapping("/edit/{id}")
	public String editPostForm(@PathVariable Long id, Model model) {
		Optional<Posts> post = postService.findByPostId(id);
		User loggedInUser = userService.getCurrentLoggedInMember();

		if (post.isPresent() && loggedInUser != null && post.get().getUser().equals(loggedInUser)) {
			model.addAttribute("post", post.get());
			return "post/editPostView"; // 게시글 수정 폼 뷰
		} else {
			// 수정 권한이 없는 경우 에러 페이지 또는 다른 처리
			return "error/editErrorView";
		}
	}
	
	/*
	 * 게시물 수정하기
	 */
	@PostMapping("/edit/{id}")
	public String updatePost(@PathVariable Long id, @ModelAttribute PostForm postForm, Model model) {
		Optional<Posts> updatedPostOptional = postService.update(id, postForm);
		User loggedInUser = userService.getCurrentLoggedInMember();

		if (updatedPostOptional.isPresent() && loggedInUser != null) {
			Posts updatedPost = updatedPostOptional.get();

			// 수정된 게시글 상세 페이지로 리다이렉트
			return "redirect:/post/detail/" + updatedPost.getPostId();
		} else {
			// 게시글이 없거나 수정 권한이 없는 경우 에러 페이지 또는 다른 처리
			return "error/editErrorView";
		}
	}
	
	/*
	 * 게시물 삭제하기
	 */
	@PostMapping("/delete/{id}")
	public String deletePost(@PathVariable Long id) {
		User loggedInUser = userService.getCurrentLoggedInMember();
		Optional<Posts> post = postService.findByPostId(id);

		boolean isDeleted = postService.deletePostById(id);
		if (isDeleted && loggedInUser != null && post.get().getUser().equals(loggedInUser)) {
			// 게시물 삭제에 성공하면 전체 게시물 목록으로 리다이렉트
			return "redirect:/post/all";
		} else {
			// 삭제할 게시물이 없는 경우 에러 페이지 또는 다른 처리
			return "error/deleteErrorView";
		}
	}

	/*
	 * 게시글 검색하기
	 * 특정 키워드를 포함하는 게시글을 검색하여 결과를 템플릿에 전달합니다.
	 */
	@GetMapping("/search")
	public String searchPostsByTitle(@RequestParam("keyword") String keyword, Model model) {
		Page<Posts> searchResults = postService.searchPostsByTitle(keyword, PageRequest.of(0, 10)); // 예시로 페이지는 첫 번째
																									// 페이지, 10개씩 표시
		model.addAttribute("allPosts", searchResults.getContent()); // 검색 결과를 posts에 저장하여 템플릿에 전달

		return "post/allPostsView"; // 여기에는 실제 게시판 페이지의 템플릿명을 입력해야 합니다.
	}
}
