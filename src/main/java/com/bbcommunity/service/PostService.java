package com.bbcommunity.service;

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
/*
* 게시물 관련 서비스 클래스입니다.
* 게시물 조회, 저장, 수정, 삭제 등의 기능을 제공합니다.
* @Service 어노테이션을 통해 스프링의 빈으로 등록되며, 필요한 리포지토리들과 UserService를 주입 받아 사용합니다.
*/
@RequiredArgsConstructor
@Service
public class PostService {
	private final PostsRepository postsRepository;
	private final BoardRepository boardRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	
	// 모든 게시물을 페이지 단위로 조회하는 메소드입니다.
	public Page<Posts> findAll(Pageable pageable) {
		return postsRepository.findAll(pageable);
	}
	
	// 특정 게시판의 게시물을 페이지 단위로 조회하는 메소드입니다.
	public Page<Posts> findByBoardBoardId(Long boardId, Pageable pageable) {
	    return postsRepository.findByBoardBoardId(boardId, pageable);
	}
	
	// 게시물 아이디로 게시물을 조회하고, 조회 수를 증가시키는 메소드입니다.
	@Transactional
	public Optional<Posts> findByPostId(Long id) {
		Optional<Posts> post = postsRepository.findById(id);
		post.ifPresent(this::incrementPostViews);
		return post;
	}

	// 게시물의 조회 수를 증가시키는 메소드입니다.
	@Transactional
	public void incrementPostViews(Posts post) {
		post.setPostViews(post.getPostViews() + 1);
		postsRepository.save(post);
	}

	// 게시물을 저장하는 메소드입니다. 게시판 아이디와 현재 로그인한 사용자 정보를 이용해 게시물을 생성합니다.
	@Transactional
	public Long save(PostForm postForm) {
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
	}

	// 게시물을 수정하는 메소드입니다. 게시물 아이디와 수정할 내용을 입력 받아 게시물을 수정합니다.
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
	
	// 게시물을 삭제하는 메소드입니다. 게시물 아이디를 입력 받아 해당 게시물을 삭제합니다.
	public boolean deletePostById(Long postId) {
        Optional<Posts> postOptional = postsRepository.findById(postId);
        if (postOptional.isPresent()) {
            // 삭제하려는 게시물이 존재하면 삭제 수행
            postsRepository.delete(postOptional.get());
            return true; // 삭제 성공
        }
        return false; // 삭제할 게시물이 없음
    }
	
	// 게시물을 제목으로 검색하는 메소드입니다.
	@Transactional
	public Page<Posts> searchPostsByTitle(String keyword, Pageable pageable) {
        return postsRepository.findByTitleIgnoreCaseContaining(keyword, pageable);
    }
}
