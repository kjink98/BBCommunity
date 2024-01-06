package com.bbcommunity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbcommunity.entity.Board;
/*
* 게시판 정보를 처리하는 레포지토리 인터페이스입니다.
* 게시판 ID를 이용하여 게시판 정보를 조회할 수 있는 메소드를 제공합니다.
* JpaRepository 인터페이스를 상속받아 기본적인 CRUD 연산을 지원합니다.
*/
public interface BoardRepository extends JpaRepository<Board, Long> {
	// 게시판 ID를 이용하여 게시판 정보를 조회하는 메소드입니다.
    Board findByBoardId(Long boardId);
}
