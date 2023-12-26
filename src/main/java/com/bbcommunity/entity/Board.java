package com.bbcommunity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "board")
@Getter
@Setter
public class Board {
	@Id
    @Column(name = "BOARD_ID")
    private int boardId;

    @Column(name = "BOARD_NAME", nullable = false)
    private String boardName;	
}
