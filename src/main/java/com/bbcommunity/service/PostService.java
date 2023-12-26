package com.bbcommunity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bbcommunity.dto.PostForm;
import com.bbcommunity.entity.Board;
import com.bbcommunity.entity.Posts;
import com.bbcommunity.entity.User;
import com.bbcommunity.repository.BoardRepository;
import com.bbcommunity.repository.PostsRepository;
import com.bbcommunity.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
	private final PostsRepository postsRepository;
	private final BoardRepository boardRepository;
	private final UserRepository userRepository;
	private final UserService userService;

	public List<Posts> findAll() {
		return postsRepository.findAll();
	}

	public List<Posts> findByBoardBoardId(Long boardId) {
		return postsRepository.findByBoardBoardId(boardId);
	}
	
	@Transactional
	public Long save(PostForm postForm) {
	    try {
	        Long boardId = postForm.getBoardId();
	        User user = userService.getCurrentLoggedInMember();
	        
	        Board board = boardRepository.findById(boardId)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid boardId:" + boardId));

	        Posts post = Posts.builder()
	            .title(postForm.getTitle())
	            .content(postForm.getContent())
	            .user(user)
	            .board(board)
	            .build();

	        return postsRepository.save(post).getPostId();
	    } catch (Exception e) {
	        System.out.println("An error occurred in the save method");
	        e.printStackTrace();
	        return null;
	    }
	    }
}
