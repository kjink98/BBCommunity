package com.bbcommunity.dto;

import lombok.Getter;
import lombok.Setter;
/*
* 댓글 관련 데이터 전송 객체(DTO)입니다.
* 사용자가 게시글에 댓글을 남길 때 사용합니다.
*/
@Getter
@Setter
public class CommentDto {
	private Long postId; // 댓글이 달릴 게시글의 ID
	private Long userId; // 댓글을 작성하는 사용자의 ID
    private String commentContent; // 사용자가 작성한 댓글 내용
}
