package com.bbcommunity.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bbcommunity.entity.Posts;

/*
* 게시글 정보를 처리하는 레포지토리 인터페이스입니다.
* 게시판 ID를 이용한 게시글 페이지 조회, 게시글 ID를 이용한 게시글 조회, 제목을 이용한 게시글 페이지 조회 등의 메소드를 제공합니다.
* JpaRepository 인터페이스를 상속받아 기본적인 CRUD 연산을 지원합니다.
*/
public interface PostsRepository extends JpaRepository<Posts, Long> {
	// 게시판 ID를 이용하여 해당 게시판의 게시글을 페이지 단위로 조회하는 메소드입니다.
	Page<Posts> findByBoardBoardId(Long boardId, Pageable pageable);
	// 게시글 ID를 이용하여 게시글 정보를 조회하는 메소드입니다.
	Optional<Posts> findByPostId(Long id);
	// 제목을 이용하여 게시글을 페이지 단위로 조회하는 메소드입니다. 제목은 대소문자를 구분하지 않습니다.
	Page<Posts> findByTitleIgnoreCaseContaining(String title, Pageable pageable);

	
	
	
	
	
}