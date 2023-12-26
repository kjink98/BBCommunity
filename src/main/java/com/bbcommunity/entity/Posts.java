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

@Getter
@NoArgsConstructor
@Entity
@Table(name = "POST")
public class Posts {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private int postId;
	
	@ManyToOne
	@JoinColumn(name = "BOARD_ID")
    private Board boardId;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
    private User userId;

    @Column(name = "POST_TITLE")
    private String title;

    @Column(name = "POST_CONTENT")
    private String content;

    @Column(name = "POST_REGDATE")
    private LocalDateTime postRegdate;

    @Column(name = "POST_VIEWS", columnDefinition = "integer default 0", nullable = false)
    private int postViews;
    
    @Builder
    public Posts(String title, String content, User userId, Board boardId){
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.boardId = boardId;
    }
    
    /**
     * 게시글 정보 업데이트
     */
    public void update(String title, String content){
        this.title=title;
        this.content=content;
    }
}
