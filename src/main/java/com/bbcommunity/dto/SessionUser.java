package com.bbcommunity.dto;

import java.io.Serializable;

import com.bbcommunity.entity.User;

import lombok.Getter;
/*
* 세션에서 사용자 정보를 저장하는데 사용하는 객체입니다.
* 사용자의 이메일과 이름 정보를 저장하며, 객체를 세션에 저장하기 위해 Serializable 인터페이스를 구현합니다.
*/
@Getter
public class SessionUser implements Serializable{
	private String email;
	private String name;
	/*
	* 사용자 객체를 받아 세션 사용자 정보를 생성하는 생성자입니다.
	*/
	public SessionUser(User user) {
		this.email = user.getEmail(); // 사용자의 이메일을 세션 사용자의 이메일로 설정
		this.name = user.getName(); // 사용자의 이름을 세션 사용자의 이름으로 설정
	}
}
