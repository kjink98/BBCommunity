package com.bbcommunity.dto;

import com.bbcommunity.role.Role;

import lombok.Getter;
import lombok.Setter;
/*
* 사용자의 역할 변경 요청에 대한 데이터 전송 객체(DTO)입니다.
* 관리자가 사용자의 역할을 변경할 때 사용합니다.
*/
@Getter
@Setter
public class UserRolesRequest {
	private String email; // 역할이 변경될 사용자의 이메일
    private Role role; // 사용자에게 할당될 새로운 역할
}
