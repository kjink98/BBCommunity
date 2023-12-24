package com.bbcommunity.dto;

import lombok.Getter;
import lombok.Setter;

/*
 * 회원가입 DTO
 */
@Getter
@Setter
public class UserRegisterDto {
	private String email;
    private String password;
    private String passwordCheck;
    private String name;
    private String gender;
    private String nickname;
}
