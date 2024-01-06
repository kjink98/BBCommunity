package com.bbcommunity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
* 사용자 정보 변경 요청에 대한 데이터 전송 객체(DTO)입니다.
* 사용자가 자신의 닉네임을 변경하고 싶을 때 사용합니다.
*/
@Getter 
@Setter 
@AllArgsConstructor  // 모든 필드 값을 파라미터로 받는 생성자를 생성
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
public class ChangeInfoRequestDto {
	private String newNickname;
}
