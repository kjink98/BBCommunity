package com.bbcommunity.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Page<Posts> findAll(Pageable pageable) {
		return postsRepository.findAll(pageable);
	}

	public Page<Posts> findByBoardBoardId(Long boardId, Pageable pageable) {
	    return postsRepository.findByBoardBoardId(boardId, pageable);
	}
	@Transactional
	public Optional<Posts> findByPostId(Long id) {
		Optional<Posts> post = postsRepository.findById(id);
		post.ifPresent(this::incrementPostViews);
		return post;
	}

	@Transactional
	public void incrementPostViews(Posts post) {
		post.setPostViews(post.getPostViews() + 1);
		postsRepository.save(post);
	}

	@Transactional
	public Long save(PostForm postForm) {
		try {
			// postForm에서 필요한 정보 추출
			Long boardId = postForm.getBoardId();
			User user = userService.getCurrentLoggedInMember();

			// 해당 정보를 이용하여 새로운 Posts 객체 생성
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
	
	@Transactional
	public Optional<Posts> update(Long id, PostForm postForm) {
	    Optional<Posts> existingPostOptional = postsRepository.findById(id);
	    if (existingPostOptional.isPresent()) {
	        Posts existingPost = existingPostOptional.get();
	        existingPost.setTitle(postForm.getTitle());
	        existingPost.setContent(postForm.getContent());

	        return Optional.of(postsRepository.save(existingPost)); // 게시글 업데이트 후 Optional로 감싸서 반환
	    } else {
	        return Optional.empty(); // 존재하지 않는 게시물이면 Optional.empty() 반환
	    }
	}
	
	public boolean deletePostById(Long postId) {
        Optional<Posts> postOptional = postsRepository.findById(postId);
        if (postOptional.isPresent()) {
            // 삭제하려는 게시물이 존재하면 삭제 수행
            postsRepository.delete(postOptional.get());
            return true; // 삭제 성공
        }
        return false; // 삭제할 게시물이 없음
    }
}
