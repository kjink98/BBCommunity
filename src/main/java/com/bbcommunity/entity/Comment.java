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
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "COMMENT")
public class Comment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long commentId;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
    private User user;

	@ManyToOne
	@JoinColumn(name = "POST_ID")
    private Posts post;
	
	@Column(name = "COMMENT_CONTENT")
    private String commentContent;

    @Column(name = "COMMENT_REGDATE")
    private LocalDateTime commentRegdate;
}
