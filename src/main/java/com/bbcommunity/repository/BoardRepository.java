package com.bbcommunity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbcommunity.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByBoardName(String boardName);
}
