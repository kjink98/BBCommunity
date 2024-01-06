package com.bbcommunity.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
/*
* 사용자의 권한을 표현하는 열거형 클래스입니다.
* USER와 ADMIN 두 가지의 권한을 가진 사용자를 표현합니다.
* 각 권한은 키(key)와 제목(title)을 가지며, 키는 권한을 대표하는 문자열, 제목은 사용자에게 보여지는 문자열입니다.
* Lombok 라이브러리의 @Getter와 @RequiredArgsConstructor를 이용하여 getter 메소드와 생성자를 자동 생성합니다.
*/
@Getter
@RequiredArgsConstructor
public enum Role {
	USER("ROLE_USER", "유저"),
	ADMIN("ROLE_ADMIN", "관리자");
	
	private final String key; // 권한을 대표하는 키
	private final String title; // 사용자에게 보여지는 권한의 제목
}
