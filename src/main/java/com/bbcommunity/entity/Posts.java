package com.bbcommunity.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/*
* 게시글 정보를 표현하는 엔티티 클래스입니다.
* 게시글 ID, 게시판, 작성자, 제목, 내용, 등록 날짜, 조회수를 필드로 가지며, 'POST' 테이블에 매핑됩니다.
*/
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "POST")
public class Posts {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long postId;
	
	@ManyToOne
	@JoinColumn(name = "BOARD_ID")
    private Board board; // 게시글이 속한 게시판
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "POST_TITLE")
    private String title;

    @Column(name = "POST_CONTENT")
    private String content;

    @Column(name = "POST_REGDATE")
    private LocalDateTime postRegdate;

    @Column(name = "POST_VIEWS", columnDefinition = "integer default 0", nullable = false)
    private int postViews;
    
    @Builder // 빌더 패턴 클래스를 자동으로 생성합니다.
    public Posts(String title, String content, User user, Board board){
    	// 게시글의 제목, 내용, 작성자, 게시판을 인자로 받는 생성자입니다.
        this.title = title;
        this.content = content;
        this.user = user;
        this.board = board;
    }
    
    
    /**
     * 게시글 정보 업데이트
     */
    public void update(String title, String content){
        this.title=title;
        this.content=content;
    }
}
