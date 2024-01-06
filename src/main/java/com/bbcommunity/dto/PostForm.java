package com.bbcommunity.dto;

import com.bbcommunity.entity.Board;
import com.bbcommunity.entity.User;

import lombok.Getter;
import lombok.Setter;
/*
* 게시글 작성에 필요한 데이터 전송 객체(DTO)입니다.
* 사용자가 게시글을 작성할 때 사용합니다.
*/
@Getter
@Setter
public class PostForm {
	private Board board; // 게시글이 작성될 게시판 정보
	private Long boardId;
	private Long userId;
	private String title;
	private String content;
	private User user; // 게시글을 작성하는 사용자 정보
}
