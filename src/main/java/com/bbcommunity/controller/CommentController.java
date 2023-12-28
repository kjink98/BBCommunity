package com.bbcommunity.controller;

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

@Controller
@RequestMapping("/comments")
public class CommentController {
	private final CommentService commentService;
	@Autowired
	private UserService userService;
	
	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	// 댓글 작성 폼을 표시하는 메서드
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
}
