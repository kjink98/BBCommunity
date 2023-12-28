package com.bbcommunity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
	private Long postId;
	private Long userId;
    private String commentContent;
}
