package com.bbcommunity.service;

import org.springframework.stereotype.Service;

import com.bbcommunity.entity.Board;
import com.bbcommunity.repository.BoardRepository;

@Service
public class BoardService {
	private final BoardRepository boardRepository;

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;	
	}

	public Board getBoardByName(String boardName) {
		return boardRepository.findByBoardName(boardName);
	}
}
