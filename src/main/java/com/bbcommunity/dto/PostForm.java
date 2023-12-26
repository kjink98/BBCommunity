package com.bbcommunity.dto;

import com.bbcommunity.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForm {
	private Long boardId;
	private Long userId;
	private String title;
	private String content;
	private User user;
}
