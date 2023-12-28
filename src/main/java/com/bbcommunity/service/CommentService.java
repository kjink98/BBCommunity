package com.bbcommunity.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbcommunity.entity.Comment;
import com.bbcommunity.entity.Posts;
import com.bbcommunity.entity.User;
import com.bbcommunity.repository.CommentRepository;

@Service
public class CommentService {
	private final CommentRepository commentRepository;
	private final PostService postService;

	@Autowired
	public CommentService(CommentRepository commentRepository, PostService postService) {
		this.commentRepository = commentRepository;
		this.postService = postService;
	}

	// 댓글 저장 메서드
	public Comment saveComment(User user, Long postId, String commentContent) {
		Optional<Posts> post = postService.findByPostId(postId);
		if (post.isPresent()) {
			Comment comment = new Comment();
			comment.setUser(user);
			comment.setPost(post.get());
			comment.setCommentContent(commentContent);
			comment.setCommentRegdate(LocalDateTime.now());
			return commentRepository.save(comment);
		} else {
			// 처리할 게시물이 없을 때 적절한 예외 처리
			return null; // 혹은 다른 방법으로 처리
		}
	}

	// 특정 게시물의 모든 댓글 조회 메서드
	public List<Comment> getCommentsByPost(Posts post) {
		return commentRepository.findByPost(post);
	}

	// 특정 사용자가 작성한 모든 댓글 조회 메서드
	public List<Comment> getCommentsByUser(User user) {
		return commentRepository.findByUser(user);
	}

	// 댓글 ID로 댓글 조회 메서드
	public Optional<Comment> getCommentById(Long commentId) {
		return commentRepository.findByCommentId(commentId);
	}

	// 댓글 삭제 메서드
	public void deleteComment(Long commentId) {
		commentRepository.deleteByCommentId(commentId);
	}

	public void updateComment(Long commentId, String updatedContent) {
		Optional<Comment> optionalComment = commentRepository.findById(commentId);

		if (optionalComment.isPresent()) {
			Comment comment = optionalComment.get();
			comment.setCommentContent(updatedContent);
			commentRepository.save(comment);
		} else {
			// 처리할 로직 추가 (예: 존재하지 않는 댓글에 대한 예외처리)
		}
	}
}
