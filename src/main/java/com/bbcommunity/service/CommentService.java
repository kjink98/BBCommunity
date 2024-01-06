package com.bbcommunity.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bbcommunity.entity.Comment;
import com.bbcommunity.entity.Posts;
import com.bbcommunity.entity.User;
import com.bbcommunity.repository.CommentRepository;
/*
* 댓글 서비스를 제공하는 클래스입니다.
* 댓글 관련 비즈니스 로직을 처리하며, Repository를 통해 데이터베이스와의 연동을 담당합니다.
* 댓글을 저장, 조회, 삭제하는 메소드를 제공합니다.
* @Service 어노테이션을 이용하여 이 클래스가 서비스 레이어의 구성요소임을 나타냅니다.
*/
@Service
public class CommentService {
	private final CommentRepository commentRepository;
	private final PostService postService;

	@Autowired
	public CommentService(CommentRepository commentRepository, PostService postService) {
		this.commentRepository = commentRepository;
		this.postService = postService;
	}
	// 댓글을 생성하고 저장하는 메소드입니다.
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
			return null; 
		}
	}

	public List<Comment> getCommentsByPost(Posts post) {
		return commentRepository.findByPost(post);
	}

	public List<Comment> getCommentsByUser(User user) {
		return commentRepository.findByUser(user);
	}

	public Optional<Comment> getCommentById(Long commentId) {
		return commentRepository.findByCommentId(commentId);
	}

	@Transactional
	public void deleteComment(Long commentId, Long postId) {
		commentRepository.deleteByCommentId(commentId, postId);
	}

}
