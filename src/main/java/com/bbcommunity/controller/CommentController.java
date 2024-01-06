package com.bbcommunity.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bbcommunity.entity.Comment;
import com.bbcommunity.entity.Posts;
import com.bbcommunity.entity.User;
import com.bbcommunity.service.CommentService;
import com.bbcommunity.service.UserService;
/*
 * CommentController 클래스는 댓글과 관련된 요청을 처리합니다.
 */
@Controller
@RequestMapping("/comments")
public class CommentController {
	private final CommentService commentService;
	@Autowired
	private UserService userService;
	/*
	 * CommentService를 주입받아 사용합니다.
	 */
	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	/*
	 * 댓글 작성 폼을 표시하는 메서드입니다.
	 * @PostMapping 어노테이션을 사용하여 "/add" 경로로 들어오는 POST 요청을 처리합니다.
	 */
	@PostMapping("/add")
	public String addComment(@ModelAttribute("newComment") Comment newComment, @RequestParam("postId") Long postId) {
		// postId 값을 받아와서 Comment 객체에 설정
		Posts post = new Posts();
		post.setPostId(postId);
		newComment.setPost(post);

		// 현재 로그인한 사용자의 정보 가져오기
		User currentUser = userService.getCurrentLoggedInMember();

		if (currentUser != null) {
			// 현재 로그인한 사용자를 댓글 작성자로 설정
			newComment.setUser(currentUser);

			// 새로운 댓글을 저장하거나 처리하는 로직
			commentService.saveComment(currentUser, postId, newComment.getCommentContent());
			// 원하는 페이지로 리다이렉트
			return "redirect:/post/detail/" + postId; // 댓글을 단 게시글의 세부사항으로 리다이렉트
		} else {
			// 사용자가 로그인하지 않은 경우 등 예외 처리
			// 예시로 에러 페이지를 보여줌
			return "errorPage";
		}

	}

	/*
	 * 댓글 삭제 처리하는 메서드입니다.
	 * @PostMapping 어노테이션을 사용하여 "/delete" 경로로 들어오는 POST 요청을 처리합니다.
	 */
	@PostMapping("/delete")
	public String deleteComment(@RequestParam("commentId") Long commentId, @RequestParam("postId") Long postId) {
		User currentUser = userService.getCurrentLoggedInMember();
		if (currentUser == null) {
			// 사용자가 로그인하지 않은 경우
			// 로그인 페이지로 리다이렉션 또는 다른 처리를 수행할 수 있습니다.
			return "redirect:/login"; // 예시로 로그인 페이지로 리다이렉션
		}

		// 댓글 작성자 가져오기
		Optional<Comment> comment = commentService.getCommentById(commentId);
		if (comment.isPresent()) {
			User commentOwner = comment.get().getUser();

			// 현재 로그인한 사용자와 댓글 작성자가 일치하는지 확인
			if (currentUser.getId().equals(commentOwner.getId())) {
				// 댓글 삭제 작업 수행
				commentService.deleteComment(commentId, postId);
				// 삭제 후 게시물 상세 페이지로 리다이렉션
				return "redirect:/post/detail/" + postId;
			} else {
				// 현재 로그인한 사용자가 댓글 작성자가 아닌 경우
				// 권한이 없음을 처리
				return "error/deleteErrorView"; // 예시로 에러 페이지로 리다이렉션
			}
		} else {
			// 댓글을 찾을 수 없는 경우 처리
			return "error/deleteErrorView"; // 예시로 에러 페이지로 리다이렉션
		}
	}
}
