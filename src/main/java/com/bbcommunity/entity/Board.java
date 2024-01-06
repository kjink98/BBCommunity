package com.bbcommunity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
/*
* 게시판 정보를 표현하는 엔티티 클래스입니다.
* 게시판 ID와 이름을 필드로 가지며, 'board' 테이블에 매핑됩니다.
*/
@Entity // JPA가 관리하는 엔티티 클래스임을 나타냅니다.
@Table(name = "board") // 'board' 테이블과 매핑됨을 나타냅니다.
@Getter
@Setter
public class Board {
	@Id // 기본 키 필드임을 나타냅니다.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성 전략을 'IDENTITY'로 설정합니다.
	@Column(name = "BOARD_ID")
	private Long boardId;	

	@Column(name = "BOARD_NAME", nullable = false)
	private String boardName;
}
