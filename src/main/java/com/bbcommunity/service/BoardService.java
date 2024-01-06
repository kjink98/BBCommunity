package com.bbcommunity.service;

import org.springframework.stereotype.Service;

import com.bbcommunity.entity.Board;
import com.bbcommunity.repository.BoardRepository;
/*
* 게시판 서비스를 제공하는 클래스입니다.
* 게시판 관련 비즈니스 로직을 처리하며, Repository를 통해 데이터베이스와의 연동을 담당합니다.
* 게시판 ID를 이용하여 게시판 정보를 가져오는 메소드를 제공합니다.
* @Service 어노테이션을 이용하여 이 클래스가 서비스 레이어의 구성요소임을 나타냅니다.
*/
@Service
public class BoardService {
	private final BoardRepository boardRepository;
	// BoardRepository를 주입받아 초기화하는 생성자입니다.
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;	
	}
	// 게시판 ID를 이용하여 게시판 정보를 가져오는 메소드입니다.
	public Board getBoardById(Long boardId) {
		return boardRepository.findByBoardId(boardId);
	}
}
