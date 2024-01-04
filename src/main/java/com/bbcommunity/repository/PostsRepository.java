package com.bbcommunity.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bbcommunity.entity.Posts;

/**
 * 게시글 정보를 다루는 Repository 인터페이스입니다.
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
    
	Page<Posts> findByBoardBoardId(Long boardId, Pageable pageable);
	
	Optional<Posts> findByPostId(Long id);
	
	Page<Posts> findByTitleIgnoreCaseContaining(String title, Pageable pageable);

	
	
	
	
	
}