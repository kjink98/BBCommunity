package com.bbcommunity.entity;

import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/*
* 댓글 정보를 표현하는 엔티티 클래스입니다.
* 댓글 ID, 작성자, 게시글, 댓글 내용, 등록 날짜를 필드로 가지며, 'COMMENT' 테이블에 매핑됩니다.
*/
@Getter
@Setter
@NoArgsConstructor
@Entity // JPA가 관리하는 엔티티 클래스임을 나타냅니다.
@Table(name = "COMMENT")
public class Comment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long commentId;
	 
	@ManyToOne // 다대일 관계임을 나타냅니다.
	@JoinColumn(name = "USER_ID")
    private User user;

	@ManyToOne
	@JoinColumn(name = "POST_ID")
    private Posts post; // 댓글이 달린 게시글
	
	@Column(name = "COMMENT_CONTENT")
    private String commentContent;

    @Column(name = "COMMENT_REGDATE")
    private LocalDateTime commentRegdate;
}
