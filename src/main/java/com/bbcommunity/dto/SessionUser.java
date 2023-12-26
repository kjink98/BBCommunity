package com.bbcommunity.dto;

import java.io.Serializable;

import com.bbcommunity.entity.User;

import lombok.Getter;

@Getter
public class SessionUser implements Serializable{
	private String email;
	private String name;
	
	public SessionUser(User user) {
		this.email = user.getEmail();
		this.name = user.getName();
	}
}
